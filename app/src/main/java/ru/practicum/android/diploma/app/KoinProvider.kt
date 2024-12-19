package ru.practicum.android.diploma.app

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.dataModule
import ru.practicum.android.diploma.di.repositoryModule
import ru.practicum.android.diploma.di.useCaseModule
import ru.practicum.android.diploma.di.utilModule
import ru.practicum.android.diploma.di.viewModelModule

class KoinProvider {
    init {
        startKoin {
            androidContext(NavigationJobApp.applicationContext())
            modules(dataModule, useCaseModule, repositoryModule, viewModelModule, utilModule)
        }
    }
}
