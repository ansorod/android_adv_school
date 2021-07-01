package com.ansorod.chat.service

import android.util.Log
import com.ansorod.chat.data.MessageRepository
import com.ansorod.chat.notification.NotificationHelper
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.koin.android.ext.android.get

class ChatFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("jamal", "New Token: $token")
        FirebaseMessaging.getInstance().subscribeToTopic("adv_chat").addOnCompleteListener { task ->
            Log.i("jamal", "Completed")
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val receivedMessage = message.data["message"]
        receivedMessage?.let { msg ->
            val json = JSONObject(msg)
            val username = json["userName"] as String ?: ""
            val text = json["text"] as String ?: ""
            val timestamp = json["timestamp"] as Long ?: 0L

            val repo: MessageRepository = get()

            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    repo.insertMessage(username, text, timestamp)
//                    NotificationHelper.notifyChatConversation(applicationContext, username, text)
                    NotificationHelper.bubbleNotification(applicationContext)
                }
            }

        }
    }
}