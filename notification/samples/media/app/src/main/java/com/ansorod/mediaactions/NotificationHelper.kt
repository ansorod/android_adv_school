package com.ansorod.mediaactions

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {

    private const val MEDIA_NOTIFICATION_CHANNEL = "media_notificationChannel"

    fun getAlbumNotification(context: Context, bitmap: Bitmap, trackTitle: String, description: String): Notification {
        val notification = NotificationCompat.Builder(context, MEDIA_NOTIFICATION_CHANNEL)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle(trackTitle)
            .setContentText(description)
            .setLargeIcon(bitmap)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())

        return notification.build()
    }

    fun createMediaNotificationChannel(context: Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "OrdinaryChannelName"
            val description = "Channel for simple notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(MEDIA_NOTIFICATION_CHANNEL, name, importance).apply {
                this.description = description
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(true)
            }

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}