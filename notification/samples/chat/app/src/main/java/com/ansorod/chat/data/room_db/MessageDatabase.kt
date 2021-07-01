package com.ansorod.chat.data.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ansorod.chat.data.model.MessageData
import com.ansorod.chat.data.model.User

@Database(entities = [MessageData::class, User::class], version = 1)
abstract class MessageDatabase: RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao

    companion object {

        private var instance: MessageDatabase? = null

        fun getDatabase(context: Context): MessageDatabase {
            if(instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MessageDatabase::class.java,
                    "MessageDB"
                ).build()
            }
            return instance as MessageDatabase
        }
    }
}