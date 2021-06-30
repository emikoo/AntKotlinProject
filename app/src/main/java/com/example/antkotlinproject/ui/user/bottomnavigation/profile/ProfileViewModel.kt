package com.example.antkotlinproject.ui.user.bottomnavigation.profile

import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.ProfileEvent
import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.repository.ProfileRepository
import com.example.antkotlinproject.utils.PrefsHelper

class ProfileViewModel(
    private val repository: ProfileRepository,
    private val prefsHelper: PrefsHelper
) : BaseViewModel<BaseEvent>() {

    init {
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        loading.value = true
        disposable.add(
            repository.fetchUserProfile()
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = ProfileEvent.UserProfileFetched(it) },
                    { message.value = it.message })
        )
    }

    fun editUserProfile(data: User) {
        loading.value = true
        disposable.add(
            repository.editUserProfile(data)
                .doOnComplete { loading.value = false }
                .subscribe(
                    { event.value = ProfileEvent.UserProfileEdited(data) },
                    { message.value = it.message })
        )
    }

    fun fetchTeacherProfile(id: Int) {
        loading.value = true
        disposable.add(
            repository.fetchTeacherProfile(id)
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = ProfileEvent.TeacherProfileFetched(it) },
                    { message.value = it.message })
        )
    }

    fun clearUserData() {
        prefsHelper.clearUserData()
    }
}