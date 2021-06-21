package com.example.antkotlinproject.base

import com.example.antkotlinproject.data.model.CategoryModel

sealed class BaseEvent {
    class Error(message: String) : BaseEvent()
    class Success<T>(result: T) : BaseEvent()
    class Loading(state: Boolean) : BaseEvent()
}

sealed class CategoryEvent : BaseEvent() {
    class  CategoryFetched(val array: MutableList<CategoryModel>?) : CategoryEvent()
}

