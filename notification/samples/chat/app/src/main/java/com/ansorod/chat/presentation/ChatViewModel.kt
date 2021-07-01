package com.ansorod.chat.presentation

import androidx.lifecycle.*
import com.ansorod.chat.data.MessageRepository
import com.ansorod.chat.data.model.User
import com.ansorod.chat.presentation.uimodel.UIMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel(private val repo: MessageRepository): ViewModel(), LifecycleObserver {

    private val _isUserRegisteredState = MutableLiveData<Boolean>()
    private val _messageList = MutableLiveData<List<UIMessage>>()

    val isUserRegisteredState: LiveData<Boolean> = _isUserRegisteredState
    val messageList: LiveData<List<UIMessage>> = _messageList

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun loadUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isUserRegisteredState.postValue(repo.loadUser() != null)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun loadMessages() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.loadMessages().collect { messageList ->
                    val resultList = messageList.map { msg ->
                        UIMessage(msg.userName, msg.text, msg.timestamp)
                    }

                    _messageList.postValue(resultList)
                }
            }
        }
    }

    fun sendMessage(text: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val user = repo.loadUser()
                user?.let {
                    repo.sendMessage(it.name, text, System.currentTimeMillis())
                }
            }
        }
    }
}