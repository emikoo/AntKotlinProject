package com.example.antkotlinproject.ui.user.bottomnavigation.profile

import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.ProfileEvent
import com.example.antkotlinproject.repository.ProfileRepository

class ProfileViewModel(private val repository: ProfileRepository) : BaseViewModel<BaseEvent>() {
    fun fetchUserProfile(id: Int) {
        loading.value = true
        disposable.add(
            repository.fetchUserProfile(id)
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = ProfileEvent.UserProfileFetched(it) },
                    { message.value = it.message })
        )
    }

}