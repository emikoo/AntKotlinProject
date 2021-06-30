package com.example.antkotlinproject.ui.user.bottomnavigation.search.categories

import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.CategoryEvent
import com.example.antkotlinproject.repository.CourseRepository

class CategoriesViewModel(private val repository: CourseRepository) : BaseViewModel<BaseEvent>() {
    fun fetchSubcategory(categoryId: Int) {
        loading.value = true
        disposable.add(
            repository.fetchSubcategory(categoryId)
                .doOnTerminate { loading.value = false }
                .subscribe(
                    { event.value = CategoryEvent.SubCategoryFetched(it) },
                    { message.value = it.message })
        )
    }
}