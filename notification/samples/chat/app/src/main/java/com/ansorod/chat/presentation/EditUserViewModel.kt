package com.ansorod.chat.presentation

import androidx.lifecycle.*
import com.ansorod.chat.data.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditUserViewModel(private val repo: MessageRepository): ViewModel(), LifecycleObserver {

    private val _userName = MutableLiveData<String?>()

    val userName: LiveData<String?> = _userName

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun loadUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val user = repo.loadUser()
                _userName.postValue(user?.name)
            }
        }
    }

    fun saveUser(username: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.saveUser(username)
            }
        }
    }
}