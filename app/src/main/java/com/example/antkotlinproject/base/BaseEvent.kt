package com.example.antkotlinproject.base

import com.example.antkotlinproject.data.model.CategoryModel
import com.example.antkotlinproject.data.model.CourseModel

sealed class BaseEvent {
    class Error(message: String) : BaseEvent()
    class Success<T>(result: T) : BaseEvent()
    class Loading(state: Boolean) : BaseEvent()
}

sealed class CategoryEvent : BaseEvent() {
    class  CategoryFetched(val array: MutableList<CategoryModel>?) : CategoryEvent()
}

sealed class CourseEvent : BaseEvent() {
    class  CoursesFetched(val array: MutableList<CourseModel>?) : CourseEvent()
}

