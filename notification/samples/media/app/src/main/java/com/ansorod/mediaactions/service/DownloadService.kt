package com.ansorod.mediaactions.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationManagerCompat
import com.ansorod.mediaactions.helper.NotificationHelper

class DownloadService: Service() {

    private var timer: CountDownTimer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { receivedIntent ->
            handleAction(receivedIntent.action ?: ACTION_START_DOWNLOAD)
        }
        return START_STICKY
    }

    private fun handleAction(action: String) {
        when(action) {
            ACTION_START_DOWNLOAD -> startDownload()
            ACTION_CANCEL_DOWNLOAD -> cancelDownload()
        }
    }

    private fun startDownload() {
        val millisecond = 1000L
        val downloadTime = 30 * millisecond

        if(timer == null) {
            timer = object: CountDownTimer(downloadTime, millisecond) {
                override fun onFinish() {
                    cancelDownload()
                }

                override fun onTick(millisUntilFinished: Long) {
                    val currentProgress = (downloadTime - millisUntilFinished) * 100 / downloadTime
                    notifyProgress(currentProgress.toInt())
                }
            }.start()
        }
    }

    private fun notifyProgress(progress: Int) {
        NotificationHelper.createDownloadNotificationChannel(this)
        val notification = NotificationHelper.getDownloadNotification(this, progress)

        NotificationManagerCompat.from(this).notify(NOTIFICATION_DOWNLOAD_ID, notification)
    }

    private fun cancelDownload() {
        timer?.cancel()
        timer = null
        NotificationManagerCompat.from(this).cancel(NOTIFICATION_DOWNLOAD_ID)
        stopSelf()
    }

    companion object {
        private const val NOTIFICATION_DOWNLOAD_ID = 11

        private const val ACTION_START_DOWNLOAD = "action_start"
        const val ACTION_CANCEL_DOWNLOAD = "action_stop"

        fun startDownload(context: Context) {
            val intent = Intent(context, DownloadService::class.java)
            intent.action = ACTION_START_DOWNLOAD
            context.startService(intent)
        }
    }

}