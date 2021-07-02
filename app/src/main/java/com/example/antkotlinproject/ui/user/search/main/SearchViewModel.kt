package com.example.antkotlinproject.ui.user.search.main

import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.CategoryEvent
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.repository.CourseRepository
import com.example.antkotlinproject.ui.profile.ProfileViewModel
import com.example.antkotlinproject.utils.toImageRequestBody
import java.io.File

class SearchViewModel(private val repository: CourseRepository) : BaseViewModel<BaseEvent>() {

    var course: MutableList<CourseModel>? = mutableListOf()

    init {
        fetchCategory()
        fetchCourses()
    }

    fun fetchCategory() {
        loading.value = true
        disposable.add(
            repository.fetchCategory()
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = CategoryEvent.CategoriesFetched(it) },
                    { message.value = it.message })
        )
    }

    fun fetchCourses() {
        loading.value = true
        disposable.add(
            repository.fetchCourses()
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = CourseEvent.CoursesFetched(it) },
                    { message.value = it.message })
        )
    }
}