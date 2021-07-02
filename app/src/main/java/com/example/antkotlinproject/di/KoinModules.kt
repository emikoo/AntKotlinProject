package com.example.antkotlinproject.di

import com.example.antkotlinproject.data.network.client.*
import com.example.antkotlinproject.repository.*
import com.example.antkotlinproject.ui.auth.AuthViewModel
import com.example.antkotlinproject.ui.auth.AuthorizationFragment
import com.example.antkotlinproject.ui.auth.DefaultViewModel
import com.example.antkotlinproject.ui.profile.ProfileViewModel
import com.example.antkotlinproject.ui.teacher.AddCourseViewModel
import com.example.antkotlinproject.ui.user.search.categories.CategoriesFragment
import com.example.antkotlinproject.ui.user.search.categories.CategoriesViewModel
import com.example.antkotlinproject.ui.user.search.main.SearchFragment
import com.example.antkotlinproject.ui.user.search.main.SearchViewModel
import com.example.antkotlinproject.ui.user.search.subcategory.SubcategoryViewModel
import com.example.antkotlinproject.ui.user.detail_course.DetailCourseViewModel
import com.example.antkotlinproject.utils.PrefsHelper
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fragmentModule = module {
    fragment { AuthorizationFragment() }
    fragment { SearchFragment() }
    fragment { CategoriesFragment() }
}

val viewModelModule = module {
    viewModel { DefaultViewModel() }
    viewModel { AuthViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { DetailCourseViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { AddCourseViewModel(get()) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { SubcategoryViewModel(get()) }
}

val repositoryModule = module {
    factory<AuthorizationRepository> { AuthorizationRepositoryImpl(get(), get()) }
    factory<CourseRepository> { CourseRepositoryImpl(get()) }
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
