package com.ansorod.chat.data

import com.ansorod.chat.data.model.MessageData
import com.ansorod.chat.data.model.User
import kotlinx.coroutines.flow.Flow

class MessageRepository(
    private val localDataSource: Local,
    private val remoteDataSource: Remote
) {

    fun loadMessages(): Flow<List<MessageData>> {
        return localDataSource.loadMessages()
    }

    suspend fun insertMessage(userName: String, text: String, timestamp: Long) {
        val message = MessageData(userName = userName, text = text, timestamp = timestamp)
        localDataSource.insertMessage(message)
    }

    suspend fun sendMessage(userName: String, text: String, timestamp: Long) {
        val message = MessageData(userName = userName, text = text, timestamp = timestamp)
        remoteDataSource.sendMessage(message)
    }

    suspend fun loadUser(): User? {
        return localDataSource.loadUser()
    }

    suspend fun saveUser(name: String) {
        val user = User(name = name)
        localDataSource.saveUser(user)
    }
}