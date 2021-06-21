package com.ansorod.interapps2.ui

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ansorod.interapps2.R
import com.ansorod.interapps2.receiver.App2BroadcastReceiver

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private val receiver = App2BroadcastReceiver()

    override fun onResume() {
        super.onResume()

        val intentFilter = IntentFilter(App2BroadcastReceiver.ACTION)
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }
}