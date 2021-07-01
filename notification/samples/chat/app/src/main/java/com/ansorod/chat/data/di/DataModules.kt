package com.ansorod.chat.data.di

import com.ansorod.chat.data.Local
import com.ansorod.chat.data.LocalDataSource
import com.ansorod.chat.data.MessageRepository
import com.ansorod.chat.data.Remote
import com.ansorod.chat.data.remote.RemoteApiFactory
import com.ansorod.chat.data.remote.RemoteDataSource
import com.ansorod.chat.data.room_db.MessageDatabase
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object DataModules {

    fun load() {
        loadKoinModules(module{
            factory<Local> {
                LocalDataSource(MessageDatabase.getDatabase(get()))
            }

            factory<Remote> {
                RemoteDataSource(RemoteApiFactory.create())
            }

            single {
                MessageRepository(get(), get())
            }
        })


    }
}