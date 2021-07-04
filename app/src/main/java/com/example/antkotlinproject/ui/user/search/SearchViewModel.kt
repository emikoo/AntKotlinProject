package com.example.antkotlinproject.ui.user.search

import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.CategoryEvent
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.repository.CourseRepository

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

    fun fetchSubcategory(categoryId: Int) {
        loading.value = true
        disposable.add(
            repository.fetchSubcategory(categoryId)
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = CategoryEvent.SubcategoryFetched(it) },
                    { message.value = it.message })
        )
    }
}