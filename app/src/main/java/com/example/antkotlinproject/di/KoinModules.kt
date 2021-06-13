package com.example.salesappkotlinproject.di

import com.example.antkotlinproject.ui.splash.SplashViewModel
import com.example.antkotlinproject.ui.auth.AuthorizationFragment
import com.example.antkotlinproject.ui.auth.AuthorizationViewModel
import com.example.antkotlinproject.utils.PrefsHelper
import com.example.notesapp.data.network.client.*
import org.koin.android.ext.koin.androidContext
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
    single { provideRetrofit(get()) }
    single { provideOkHttpClient(get(), get()) }
    single { provideHttpLoginingInterceptor() }
    single { provideTokenAuthenticator(get()) }
    single { provideHeadersInterceptor(get()) }
    single { provideAuthApi(get()) }
    single { PrefsHelper(androidContext()) }
}
