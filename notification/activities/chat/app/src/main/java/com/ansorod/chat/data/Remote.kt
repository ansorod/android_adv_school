package com.ansorod.chat.data

import com.ansorod.chat.data.model.MessageData

interface Remote {

    suspend fun sendMessage(messageData: MessageData)
}