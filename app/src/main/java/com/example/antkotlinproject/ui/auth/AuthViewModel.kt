package com.example.antkotlinproject.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.ProfileEvent
import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.data.network.client.ResponseResultStatus
import com.example.antkotlinproject.repository.AuthorizationRepository
import com.example.antkotlinproject.utils.PrefsHelper
import com.example.antkotlinproject.utils.showToast
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthorizationRepository,
    private val preferences: PrefsHelper
) :
    BaseViewModel<BaseEvent>() {

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
                            login(user.username.toString(), user.password1.toString())
                        }
                        ResponseResultStatus.ERROR -> {
                            message.value = it.message
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
                            fetchIsStuff()
                        }
                        ResponseResultStatus.ERROR -> {
                            it.message.let { error.value = it }
                        }
                    }
                }
        }
    }

    fun fetchIsStuff() {
        loading.value = true
        disposable.add(
            repository.fetchIsStuff()
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = ProfileEvent.UserIsStuffFetched(it) },
                    { message.value = it.message })
        )
    }
}