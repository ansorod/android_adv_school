package com.ansorod.chat.presentation.di

import com.ansorod.chat.presentation.ChatViewModel
import com.ansorod.chat.presentation.EditUserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object UIModules {

    fun load() {
        loadKoinModules( module {
            viewModel { EditUserViewModel(get()) }
            viewModel { ChatViewModel(get()) }
        })
    }
}