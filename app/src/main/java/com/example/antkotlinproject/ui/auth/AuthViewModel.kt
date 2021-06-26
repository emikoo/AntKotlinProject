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

class AuthViewModel(
    private val repository: AuthorizationRepository,
    private val preferences: PrefsHelper
) :
    BaseViewModel<BaseEvent>() {

    val actionLoginNewScreen = MutableLiveData<Boolean>()
    val actionRegistrationNewScreen = MutableLiveData<Boolean>()
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
                            actionRegistrationNewScreen.value = true
                        }
                        ResponseResultStatus.ERROR -> {
                            actionRegistrationNewScreen.value = false
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
                            preferences.saveAccessToken(it?.result?.accessToken)
                            preferences.saveRefreshToken(it?.result?.refreshToken)
                            actionLoginNewScreen.value = true
                        }
                        ResponseResultStatus.ERROR -> {
                            it.message.let { error.value = it }
                            actionLoginNewScreen.value = false
                        }
                    }
                }
        }
    }
}