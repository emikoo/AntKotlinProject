package com.example.antkotlinproject.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

abstract class BaseActivity<ViewModel : BaseViewModel, ViewBinding : androidx.viewbinding.ViewBinding>(
    private val clazz: KClass<ViewModel>
) : AppCompatActivity() {

    lateinit var binding: ViewBinding
    abstract fun getViewBinding(): ViewBinding
    lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        viewModel = getViewModel(clazz = clazz)
        setupViews()
        subscribeToLiveData()
    }

    abstract fun setupViews()
    abstract fun subscribeToLiveData()
}