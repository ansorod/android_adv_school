package com.ansorod.chat.data

import com.ansorod.chat.data.model.MessageData
import com.ansorod.chat.data.model.User
import com.ansorod.chat.data.room_db.MessageDatabase
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val database: MessageDatabase): Local {

    private val messageDao = database.messageDao()
    private val userDao = database.userDao()

    override suspend fun insertMessage(message: MessageData) {
        messageDao.insertMessage(message)
    }

    override fun loadMessages(): Flow<List<MessageData>> {
        return messageDao.getAll()
    }

    override suspend fun saveUser(user: User) {
        userDao.insert(user)
    }

    override suspend fun loadUser(): User? {
        return userDao.getUser()
    }
}