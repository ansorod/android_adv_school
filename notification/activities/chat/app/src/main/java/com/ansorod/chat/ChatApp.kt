package com.ansorod.chat

import android.app.Application
import com.ansorod.chat.data.di.DataModules
import com.ansorod.chat.presentation.di.UIModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChatApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ChatApp)
        }

        loadModules()
    }

    private fun loadModules() {
        DataModules.load()
        UIModules.load()
    }
}