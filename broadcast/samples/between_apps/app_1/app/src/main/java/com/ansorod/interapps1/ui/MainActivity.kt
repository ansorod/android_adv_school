package com.ansorod.interapps1.ui

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ansorod.interapps1.R
import com.ansorod.interapps1.receiver.App1BroadcastReceiver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        sendInternalButton.setOnClickListener {
            sendInternalBroadcast()
        }

        sendExternalButton.setOnClickListener {
            sendExternalBroadcast()
        }
    }

    private fun sendInternalBroadcast() {
        val intentManifest = Intent(this, App1BroadcastReceiver::class.java)
        intentManifest.putExtra(App1BroadcastReceiver.EXTRA_MESSAGE, "This is an ordinary message sent locally to a Manifest registered receiver")

        val intentActivity = Intent(App1BroadcastReceiver.ACTION)
        intentActivity.putExtra(App1BroadcastReceiver.EXTRA_MESSAGE, "This is an ordinary message sent locally to a Context registered receiver")

        sendBroadcast(intentManifest)
        sendBroadcast(intentActivity)
    }

    private fun sendExternalBroadcast() {
        val externalIntent = Intent("com.ansorod.interapps2.App2BroadcastReceiver.ACTION")
        externalIntent.`package` = "com.ansorod.interapps2"
        externalIntent.putExtra("EXTRA_EXTERNAL_MESSAGE", "This is an external message")

        sendBroadcast(externalIntent)
    }

    private val receiver = App1BroadcastReceiver()

    override fun onResume() {
        super.onResume()

        val filter = IntentFilter(App1BroadcastReceiver.ACTION)

        registerReceiver(receiver, filter)
    }

    override fun onPause() {
        super.onPause()

        unregisterReceiver(receiver)
    }
}