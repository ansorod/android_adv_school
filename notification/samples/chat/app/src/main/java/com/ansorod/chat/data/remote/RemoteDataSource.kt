package com.ansorod.chat.data.remote

import android.util.Log
import com.ansorod.chat.data.Remote
import com.ansorod.chat.data.model.MessageData
import com.google.gson.Gson

class RemoteDataSource(private val remoteApi: RemoteApi): Remote {

    override suspend fun sendMessage(messageData: MessageData) {
        val topic = "/topics/adv_chat"

        val message = Gson().toJson(messageData)
        val messageRemote = MessageRemote(message = message)

        val fcm = FCMBody(topic, messageRemote)

        val result = remoteApi.sendMessage(fcm)

        Log.i("jamal", "lo")
    }
}