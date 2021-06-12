package com.example.salesappkotlinproject.di

import com.example.antkotlinproject.ui.splash.SplashViewModel
import com.example.antkotlinproject.ui.auth.AuthorizationFragment
import com.example.antkotlinproject.ui.auth.AuthorizationViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val fragmentModule = module {
    fragment { AuthorizationFragment() }
}

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { AuthorizationViewModel() }
}

val repositoryModule = module {
}

val networkRepository = module {
}
