package ru.practicum.android.diploma.app

import android.app.Application
import android.content.Context
import ru.practicum.android.diploma.BuildConfig

class NavigationJobApp : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        KoinProvider()
    }

    companion object {
        private var instance: NavigationJobApp? = null
        const val HEAD_HUNTER_TOKEN = BuildConfig.HH_ACCESS_TOKEN

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}
