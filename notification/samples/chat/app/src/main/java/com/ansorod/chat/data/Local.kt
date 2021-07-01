package com.ansorod.chat.data

import com.ansorod.chat.data.model.MessageData
import com.ansorod.chat.data.model.User
import kotlinx.coroutines.flow.Flow

interface Local {

    suspend fun insertMessage(message: MessageData)

    fun loadMessages(): Flow<List<MessageData>>

    suspend fun saveUser(user: User)

    suspend fun loadUser(): User?
}