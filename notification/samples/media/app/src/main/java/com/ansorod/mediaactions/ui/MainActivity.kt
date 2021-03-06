package com.ansorod.mediaactions.ui

import android.app.NotificationManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.ansorod.mediaactions.R
import com.ansorod.mediaactions.helper.NotificationHelper
import com.ansorod.mediaactions.service.DownloadService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        val adapter = CoverAdapter({ coverRes ->

            NotificationHelper.createMediaNotificationChannel(this)
            val notification = NotificationHelper.getAlbumNotification(this, BitmapFactory.decodeResource(resources, coverRes), "Track title", "Artist - Album")
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(0, notification)

        }, getCoverList())

        val layoutManager = GridLayoutManager(this, 2)

        albumCoverRecycleView.adapter = adapter
        albumCoverRecycleView.layoutManager = layoutManager

        downloadButton.setOnClickListener {
            DownloadService.startDownload(this)
        }
    }

    private fun getCoverList(): List<Int> {
        return arrayListOf(
            R.drawable.cover_000,
            R.drawable.cover_001,
            R.drawable.cover_002,
            R.drawable.cover_003,
            R.drawable.cover_004,
            R.drawable.cover_005,
            R.drawable.cover_006,
            R.drawable.cover_007,
            R.drawable.cover_008,
            R.drawable.cover_009,
            R.drawable.cover_010,
            R.drawable.cover_011,
            R.drawable.cover_012,
        )
    }
}