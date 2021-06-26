package com.example.antkotlinproject.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel<EVENT : BaseEvent>() : ViewModel() {
    var message = MutableLiveData<String>()

    val toast: String = "Hello"

    val loading = MutableLiveData<Boolean>()
    val event = MutableLiveData<EVENT>()

    val disposable: CompositeDisposable by lazy {
        return@lazy CompositeDisposable()
    }


}