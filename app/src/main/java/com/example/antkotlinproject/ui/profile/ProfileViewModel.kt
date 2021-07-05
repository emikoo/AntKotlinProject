package com.example.antkotlinproject.ui.profile

import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.ProfileEvent
import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.repository.ProfileRepository
import com.example.antkotlinproject.utils.PrefsHelper
import com.example.antkotlinproject.utils.toImageRequestBody
import java.io.File

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
                    { event.value = ProfileEvent.UserProfileEdited(it) },
                    { message.value = it.message })
        )
    }

    fun clearUserData() {
        prefsHelper.clearUserData()
    }

    fun changeImage(file: File) {
        loading.value = true
        disposable.add(
            repository.changeImage(file.toImageRequestBody(AVATAR))
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = ProfileEvent.UserAvatarChanged(it) },
                    { message.value = it.message }
                )
        )
    }

    companion object {
        const val AVATAR = "avatar"
    }
}