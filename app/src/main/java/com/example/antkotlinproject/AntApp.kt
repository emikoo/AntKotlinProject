package com.example.antkotlinproject

import android.app.Application
import com.example.antkotlinproject.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules

class AntApp: Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@AntApp)
            inject()
        }
    }

    fun inject() = loadKoinModules

    private val loadKoinModules by lazy {
        loadKoinModules(
            listOf(
                networkRepository,
                repositoryModule,
                fragmentModule,
                viewModelModule
            )
        )
    }
}