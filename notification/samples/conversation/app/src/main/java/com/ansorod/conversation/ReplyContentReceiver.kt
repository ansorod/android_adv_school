package com.ansorod.conversation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.RemoteInput

class ReplyContentReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            NotificationHelper.confirmResponse(context)

            if(intent != null) {
                val value = RemoteInput.getResultsFromIntent(intent).getCharSequence(NotificationHelper.KEY_REPLY_CONTENT)
                Log.i("jamal", "Result: $value")
            }
        }

    }

    companion object {
        const val EXTRA_CONVERSATION_ID = "EXTRA_CONVERSATION_ID"
    }
}