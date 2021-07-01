package com.ansorod.chat.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.content.LocusIdCompat
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.ansorod.chat.R
import com.ansorod.chat.receiver.ReplyContentReceiver
import com.ansorod.chat.ui.EditUserActivity
import com.ansorod.chat.ui.MainActivity

object NotificationHelper {

    private const val NOTIFICATION_CHANNEL = "ChatNotificationChannel"
    const val EXTRA_CONVERSATION_ID = "EXTRA_CONVERSATION_ID"
    const val KEY_REPLY_CONTENT = "KEY_REPLY_CONTENT"

    fun createNotificationChannel(context: Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Chat notification channel"
            val channelDescription = "Hold chat notifications"

            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(NOTIFICATION_CHANNEL, channelName, importance).apply {
                this.description = channelDescription
                enableLights(true)
                lightColor = Color.BLUE
                setAllowBubbles(true)
            }

            NotificationManagerCompat.from(context).createNotificationChannel(channel)
        }
    }

    fun notifyChatConversation(context: Context, userName: String, text: String) {
        val action = NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send,
            context.getString(R.string.notification_action_reply),
            getPendingIntent(context))
            .addRemoteInput(getRemoteInput(context))
            .build()

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(userName)
            .setContentText(text)
            .addAction(action)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()

        createNotificationChannel(context)
        NotificationManagerCompat.from(context).notify(0, notification)
    }

    fun bubbleNotification(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val partner = Person.Builder()
            .setName("Cesar School")
            .setImportant(true)
            .build()

        val shortcutIntent = Intent(context, MainActivity::class.java)
        shortcutIntent.action = Intent.ACTION_VIEW

        val shortcut = ShortcutInfoCompat.Builder(context, "0")
            .setLocusId(LocusIdCompat("0"))
            .setActivity(ComponentName(context, MainActivity::class.java))
            .setShortLabel("Cesar School")
            .setIcon(IconCompat.createWithResource(context, R.drawable.cover_012))
            .setLongLived(true)
            .setCategories(setOf("com.example.android.bubbles.category.TEXT_SHARE_TARGET"))
            .setIntent(shortcutIntent)
            .setPerson(partner)
            .build()

        ShortcutManagerCompat.addDynamicShortcuts(context, listOf(shortcut))

        val bubble = NotificationCompat.BubbleMetadata.Builder(pendingIntent, IconCompat.createWithResource(context, R.drawable.cover_012))
            .setDesiredHeight(600)
            .setAutoExpandBubble(true)
            .build()

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setBubbleMetadata(bubble)
            .setContentIntent(pendingIntent)
            .setShortcutId("0")
            .setLocusId(LocusIdCompat("0"))
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .addPerson(partner)
            .setStyle(NotificationCompat.MessagingStyle(partner))

        createNotificationChannel(context)
        NotificationManagerCompat.from(context).notify(0, builder.build())
    }

    fun getRemoteInput(context: Context): RemoteInput {
        return RemoteInput.Builder(KEY_REPLY_CONTENT).run {
            setLabel(context.getString(R.string.notification_action_reply))
            build()
        }
    }

    fun getPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, ReplyContentReceiver::class.java)
        intent.putExtra(EXTRA_CONVERSATION_ID, 0)
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun confirmResponse(context: Context) {
        NotificationManagerCompat.from(context).cancel(0)
    }
}