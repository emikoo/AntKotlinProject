package com.example.antkotlinproject.ui.detail_course

import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.repository.CourseRepository

class DetailCourseViewModel(private val repository: CourseRepository) : BaseViewModel<BaseEvent>() {

    fun fetchCourse(id: Int) {
        loading.value = true
        disposable.add(
            repository.fetchCourse(id)
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = CourseEvent.CourseFetched(it) },
                    { message.value = it.message })
        )
    }
}