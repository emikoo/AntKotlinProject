package com.example.antkotlinproject.di

import com.example.antkotlinproject.repository.*
import com.example.antkotlinproject.ui.auth.AuthViewModel
import com.example.antkotlinproject.ui.auth.AuthorizationFragment
import com.example.antkotlinproject.ui.auth.DefaultViewModel
import com.example.antkotlinproject.ui.user.bottomnavigation.profile.ProfileViewModel
import com.example.antkotlinproject.ui.user.bottomnavigation.search.SearchFragment
import com.example.antkotlinproject.ui.user.bottomnavigation.search.SearchViewModel
import com.example.antkotlinproject.ui.user.detail_course.DetailCourseViewModel
import com.example.antkotlinproject.utils.PrefsHelper
import com.example.notesapp.data.network.client.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fragmentModule = module {
    fragment { AuthorizationFragment() }
    fragment { SearchFragment() }
}

val viewModelModule = module {
    viewModel { DefaultViewModel() }
    viewModel { AuthViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { DetailCourseViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}

val repositoryModule = module {
    factory<AuthorizationRepository> { AuthorizationRepositoryImpl(get(), get()) }
    factory<SearchRepository> { SearchRepositoryImpl(get()) }
    factory<ProfileRepository> { ProfileRepositoryImpl(get()) }
}

val networkRepository = module {
    single { provideRetrofit(get()) }
    single { provideOkHttpClient(get(), get()) }
    single { provideHttpLoginingInterceptor() }
    single { provideTokenAuthenticator(get()) }
    single { provideHeadersInterceptor(get()) }
    single { provideAuthApi(get()) }
    single { provideCourseApi(get()) }
    single { provideProfileApi(get()) }
    single { PrefsHelper(androidContext()) }
}
