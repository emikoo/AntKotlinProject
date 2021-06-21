package com.example.antkotlinproject.ui.user.bottomnavigation.search

import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.base.CategoryEvent
import com.example.antkotlinproject.data.network.client.ResponseResultStatus
import com.example.antkotlinproject.repository.SearchRepository

class SearchViewModel(private val repository: SearchRepository) : BaseViewModel<BaseEvent>() {

    fun fetchCategory() {
        repository.fetchCategory().observeForever {
            when (it.status) {
                ResponseResultStatus.ERROR -> message.value = it.message
                ResponseResultStatus.SUCCESS -> event.value =
                    CategoryEvent.CategoryFetched(it.result)
            }
//        loading.value = true
//        disposable.add(
//            repository.fetchCategory()
//                .doOnTerminate { loading.value = false }
//                .subscribe(
//                    { event.value = CategoryEvent.CategoryFetched(it) },
//                    { message.value = it.message })
//        )
        }
    }
}