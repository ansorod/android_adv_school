package com.ansorod.systembroadcasts.ui

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ansorod.systembroadcasts.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    private val bluetoothReceiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { receivedIntent ->
                val state = receivedIntent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)
                setState(bluetoothStateValue, state == BluetoothAdapter.STATE_ON)
            }
        }
    }

    private val airplaneModeReceiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { receivedIntent ->
                val state = receivedIntent.getBooleanExtra("state", false)
                setState(airplaneModeStateValue, state)
            }
        }
    }

    private fun setState(textView: TextView, isActive: Boolean) {
        textView.setText(getStateText(isActive))
    }

    private fun getStateText(isActive: Boolean): Int {
        return if(isActive) R.string.state_active else R.string.state_inactive
    }

    override fun onResume() {
        super.onResume()

        val bluetoothFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(bluetoothReceiver, bluetoothFilter)

        val airplaneFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneModeReceiver, airplaneFilter)
    }

    override fun onPause() {
        super.onPause()

        unregisterReceiver(bluetoothReceiver)
        unregisterReceiver(airplaneModeReceiver)
    }
}