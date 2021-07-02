package com.example.antkotlinproject.ui.user.search.subcategory

import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.repository.CourseRepository

class SubcategoryViewModel(private val repository: CourseRepository): BaseViewModel<BaseEvent>(){
    fun fetchSubcategoryCourses(id: Int) {
        loading.value = true
        disposable.add(
            repository.fetchSubcategoryCourses(id)
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = CourseEvent.SubcategoryCoursesFetched(it) },
                    { message.value = it.message })
        )
    }
}