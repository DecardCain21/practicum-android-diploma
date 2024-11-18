package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.sharing.SharingInteract
import ru.practicum.android.diploma.domain.sharing.impl.SharingInteractImpl
import ru.practicum.android.diploma.domain.usecase.GetVacanciesUseCase
import ru.practicum.android.diploma.domain.usecase.GetVacancyDetailsUseCase

val interactorModule = module {

    single {
        GetVacanciesUseCase(get())
    }

    single {
        GetVacancyDetailsUseCase(get())
    }

    single<SharingInteract> {
        SharingInteractImpl(get())
    }
}
