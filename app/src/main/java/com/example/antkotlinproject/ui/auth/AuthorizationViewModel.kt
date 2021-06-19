package com.example.antkotlinproject.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.data.network.client.ResponseResultStatus
import com.example.antkotlinproject.repository.AuthorizationRepository
import com.example.antkotlinproject.utils.PrefsHelper
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthorizationRepository, private val preferences: PrefsHelper) :
    BaseViewModel<BaseEvent>() {

    val actionNewScreen = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    init {
        message = MutableLiveData()
    }

    fun regUser(user: User) {
        viewModelScope.launch {
            repository.regUser(user)
                .observeForever {
                    when (it.status) {
                        ResponseResultStatus.SUCCESS -> {
                            actionNewScreen.value = true
                        }
                        ResponseResultStatus.ERROR ->{
                            it.message.let { error.value = it }
                            actionNewScreen.value = false
                        }
                    }
                }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            repository.login(username, password)
                .observeForever {
                    when (it.status) {
                        ResponseResultStatus.SUCCESS -> {
                            preferences.saveToken(it?.result?.accessToken, it?.result?.refreshToken)
                            actionNewScreen.value = true
                        }
                        ResponseResultStatus.ERROR ->{
                            it.message.let { error.value = it }
                            actionNewScreen.value = false
                        }
                    }
                }
        }
    }
}