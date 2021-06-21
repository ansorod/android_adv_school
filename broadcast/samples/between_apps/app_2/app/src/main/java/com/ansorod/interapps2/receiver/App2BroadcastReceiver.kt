package com.ansorod.interapps2.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

class App2BroadcastReceiver: BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            Log.i("jamal", "onReceive - ACTION: ${it.action}")
            printExtras(it.extras)
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
        const val ACTION = "com.ansorod.interapps2.App2BroadcastReceiver.ACTION"
    }
}