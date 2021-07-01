package com.ansorod.conversation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput

object NotificationHelper {

    private const val CHAT_NOTIFICATION_CHANNEL = "chat_notification_channel"
    const val KEY_REPLY_CONTENT = "KEY_REPLY_CONTENT"

    fun createNotificationChannel(context: Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Chat notification channel"
            val channelDescription = "Hold chat notifications"

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHAT_NOTIFICATION_CHANNEL, channelName, importance).apply {
                this.description = channelDescription
                enableLights(true)
                lightColor = Color.BLUE
            }

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun notifyChatConversation(context: Context, conversation: String) {
        val action = NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send,
            context.getString(R.string.notification_action_reply),
            getPendingIntent(context))
            .addRemoteInput(getRemoteInput(context))
            .build()

        val notification = NotificationCompat.Builder(context, CHAT_NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Android")
            .setContentText(conversation)
            .addAction(action)
            .build()

        createNotificationChannel(context)
        NotificationManagerCompat.from(context).notify(0, notification)
    }

    fun confirmResponse(context: Context) {
        val confirmation = NotificationCompat.Builder(context, CHAT_NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText("Replied")
            .build()

        NotificationManagerCompat.from(context).notify(0, confirmation)
    }

    fun getRemoteInput(context: Context): RemoteInput {
        return RemoteInput.Builder(KEY_REPLY_CONTENT).run {
            setLabel(context.getString(R.string.notification_action_reply))
            build()
        }
    }

    fun getPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, ReplyContentReceiver::class.java)
        intent.putExtra(ReplyContentReceiver.EXTRA_CONVERSATION_ID, 0)
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}