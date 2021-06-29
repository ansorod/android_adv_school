package com.ansorod.simplenotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        notifyButton.setOnClickListener {
            createNotificationChannel()

            val groupNotifications = System.currentTimeMillis() % 2 == 0L
            createNotification(notificationBodyInput.text.toString(), groupNotifications)
        }
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "OrdinaryChannelName"
            val description = "Channel for simple notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL, name, importance).apply {
                this.description = description
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(true)
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(text: String, groupNotification: Boolean = false) {
        val intent = Intent(this, FromNotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        intent.putExtra(FromNotificationActivity.EXTRA_TEXT, text)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Title")
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setAutoCancel(true)

        if(groupNotification) {
            builder.setGroup(GROUP_NAME_1)
        }

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(System.currentTimeMillis().toInt(), builder.build())

//        createSummary()
    }

    private fun createSummary() {
        val summary = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
            .setContentTitle("Summary of your notifications")
            .setContentText("You've got new messages")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setGroup(GROUP_NAME_1)
            .setGroupSummary(true)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(12, summary)
    }

    companion object {
        private val NOTIFICATION_CHANNEL = "25062021_simple_notification"
        private val GROUP_NAME_1 = "NOTIFICATION_GROUP_1"
    }
}