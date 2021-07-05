package com.example.antkotlinproject.ui.user.teacher_s_profile

import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.ProfileEvent
import com.example.antkotlinproject.repository.ProfileRepository

class TeacherProfileViewModel(private val repository: ProfileRepository): BaseViewModel<BaseEvent>() {
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
}