package com.example.antkotlinproject.ui.my_courses

import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.repository.CourseRepository

class MyCoursesViewModel(private val repository: CourseRepository): BaseViewModel<BaseEvent>() {
    var course: MutableList<CourseModel>? = mutableListOf()
    init {
        fetchUserCourses()
    }

    fun fetchUserCourses() {
        loading.value = true
        disposable.add(
            repository.fetchUserCourses()
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = CourseEvent.UserCoursesFetched(it) },
                    { message.value = it.message })
        )
    }
}