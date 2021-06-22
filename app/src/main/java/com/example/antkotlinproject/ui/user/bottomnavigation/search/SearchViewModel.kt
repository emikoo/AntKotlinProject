package com.example.antkotlinproject.ui.user.bottomnavigation.search

import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.CategoryEvent
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.repository.SearchRepository

class SearchViewModel(private val repository: SearchRepository) : BaseViewModel<BaseEvent>() {

    init {
        fetchCategory()
        fetchCourses()
    }

    private fun fetchCategory() {
        loading.value = true
        disposable.add(
            repository.fetchCategory()
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = CategoryEvent.CategoryFetched(it) },
                    { message.value = it.message })
        )
    }

    private fun fetchCourses() {
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