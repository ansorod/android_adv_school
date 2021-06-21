package com.ansorod.interapps1.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

class App1BroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let { receivedIntent ->
            Log.i("jamal", "onReceive - action: ${receivedIntent.action}")

            printExtras(receivedIntent.extras)
        }
    }

    private fun printExtras(extrasBundle: Bundle?) {
        if(extrasBundle == null) {
            Log.i("jamal", "Received bundle is null")
            return
        }

        val keySet = extrasBundle.keySet()
        for(key in keySet) {
            Log.i("jamal", "KEY: $key - VALUE: ${extrasBundle[key]}")
        }
    }

    companion object {

        const val ACTION = "com.ansorod.interapps1.App1BroadcastReceiver.ACTION"
        const val EXTRA_MESSAGE = "com.ansorod.interapps1.App1BroadcastReceiver.EXTRA_MESSAGE"
    }

}