package com.ansorod.mediaactions.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.ansorod.mediaactions.R
import com.ansorod.mediaactions.service.DownloadService

object NotificationHelper {

    private const val MEDIA_NOTIFICATION_CHANNEL = "media_notificationChannel"
    private const val DOWNLOAD_NOTIFICATION_CHANNEL = "download_notificationChannel"

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
        createNotificationChannel(context,
            "Media Notifications",
            MEDIA_NOTIFICATION_CHANNEL,
            "Check out your music player notifications"
        )
    }

    fun createDownloadNotificationChannel(context: Context) {
        createNotificationChannel(context,
            "Download service notifications",
            DOWNLOAD_NOTIFICATION_CHANNEL,
            "Notify you about your download progress"
        )
    }

    fun getDownloadNotification(context: Context, downloadProgress: Int): Notification {
        val downloadLayout = RemoteViews(context.packageName, R.layout.notification_download)
        downloadLayout.setProgressBar(R.id.downloadProgress, 100, downloadProgress, false)
        downloadLayout.setTextViewText(R.id.downloadProgresstext,
            context.getString(R.string.notification_download_progress, downloadProgress.toString()))

//        downloadLayout.setOnClickPendingIntent()
        val cancelDownloadIntent = Intent(context, DownloadService::class.java)
        cancelDownloadIntent.action = DownloadService.ACTION_CANCEL_DOWNLOAD

        val pendingIntent = PendingIntent.getService(context, 0, cancelDownloadIntent, 0)

        val cancelAction = NotificationCompat.Action.Builder(android.R.drawable.ic_menu_close_clear_cancel,
            context.getString(R.string.notification_action_cancel),
            pendingIntent
        )


        val downloadNotification = NotificationCompat.Builder(context, DOWNLOAD_NOTIFICATION_CHANNEL)
            .setSmallIcon(android.R.drawable.arrow_down_float)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(downloadLayout)
            .addAction(cancelAction.build())

        return downloadNotification.build()
    }

    private fun createNotificationChannel(context: Context, channelName: String, channelId: String, channelDescription: String) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                this.description = channelDescription
                enableLights(true)
                lightColor = Color.BLUE
            }

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}