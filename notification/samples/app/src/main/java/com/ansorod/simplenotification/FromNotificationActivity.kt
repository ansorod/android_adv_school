package com.ansorod.simplenotification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_from_notification.*

class FromNotificationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_from_notification)

        val text = intent.getStringExtra(EXTRA_TEXT) ?: ""
        receivedTextView.text = text
    }

    companion object {
        const val EXTRA_TEXT = "extra_text"
    }
}