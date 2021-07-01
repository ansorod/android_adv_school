package com.ansorod.chat.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.RemoteInput
import com.ansorod.chat.data.MessageRepository
import com.ansorod.chat.notification.NotificationHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ReplyContentReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            NotificationHelper.confirmResponse(context)

            if(intent != null) {
                val value = RemoteInput.getResultsFromIntent(intent).getCharSequence(NotificationHelper.KEY_REPLY_CONTENT).toString()

                MessageStore().run {
                    storeMessage(value)
                }
            }
        }
    }

    class MessageStore: KoinComponent {

        fun storeMessage(message: String) {

            GlobalScope.launch {
                val repo: MessageRepository = get()
                repo.loadUser()?.let { user ->
                    repo.sendMessage(user.name, message, System.currentTimeMillis())
                }
            }
        }
    }
}