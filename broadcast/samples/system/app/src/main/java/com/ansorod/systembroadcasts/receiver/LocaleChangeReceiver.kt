package com.ansorod.systembroadcasts.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

class LocaleChangeReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let { receivedIntent ->
            Log.i("jamal", "onReceive: ACTION -> ${receivedIntent.action}")
            printExtras(receivedIntent.extras)
        }
    }

    private fun printExtras(bundleExtras: Bundle?) {
        if(bundleExtras == null) {
            Log.i("jamal", "Bundle is empty")
            return
        }

        val keySet =  bundleExtras.keySet()
        for(key in keySet) {
            Log.i("jamal", "KEY: $key - VALUE: ${bundleExtras[key]}")
        }
    }
}