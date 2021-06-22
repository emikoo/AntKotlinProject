package com.example.antkotlinproject.ui.user.detail_course

import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.data.network.client.ResponseResultStatus
import com.example.antkotlinproject.repository.SearchRepository

class DetailCourseViewModel(private val repository: SearchRepository) : BaseViewModel<BaseEvent>() {

    fun fetchCourse(id: Int) {
        repository.fetchCourse(id).observeForever {
            when (it.status) {
                ResponseResultStatus.ERROR -> message.value = it.message
                ResponseResultStatus.SUCCESS -> event.value =
                    CourseEvent.CourseFetched(it.result)
            }
        }
    }
}