package com.ansorod.chat.data.room_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ansorod.chat.data.model.MessageData
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert
    fun insertMessage(messageData: MessageData)

    @Query("SELECT * FROM MessageData ORDER BY MessageData.timestamp DESC")
    fun getAll(): Flow<List<MessageData>>
}