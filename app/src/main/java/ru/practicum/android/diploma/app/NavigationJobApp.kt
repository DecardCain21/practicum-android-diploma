package ru.practicum.android.diploma.app

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.di.useCaseModule
import ru.practicum.android.diploma.di.dataModule
import ru.practicum.android.diploma.di.repositoryModule
import ru.practicum.android.diploma.di.utilModule
import ru.practicum.android.diploma.di.viewModelModule

class NavigationJobApp : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NavigationJobApp)
            modules(dataModule, useCaseModule, repositoryModule, viewModelModule, utilModule)
        }

    }

    companion object {
        private var instance: NavigationJobApp? = null
        const val HEAD_HUNTER_TOKEN = BuildConfig.HH_ACCESS_TOKEN

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}
