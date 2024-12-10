package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.FavoriteVacanciesRepositoryImpl
import ru.practicum.android.diploma.data.IndustryRepositoryImpl
import ru.practicum.android.diploma.data.CountryRepositoryImpl
import ru.practicum.android.diploma.data.VacancyRepositoryImpl
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.api.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.api.CountryRepository
import ru.practicum.android.diploma.domain.api.VacancyRepository

val repositoryModule = module {

    single<VacancyRepository> {
        VacancyRepositoryImpl(
            networkClient = get(),
            vacancyMapper = get(),
            vacancyDetailsMapper = get(),
            optionMapper = get()
        )
    }

    single<FavoriteVacanciesRepository> {
        FavoriteVacanciesRepositoryImpl(
            favoriteDao = get<AppDatabase>().favoriteVacanciesDao(),
            convertor = get()
        )
    }

    single<IndustryRepository> {
        IndustryRepositoryImpl(
            networkClient = get(),
            industryMapper = get()
        )
    }

    single<CountryRepository> {
        CountryRepositoryImpl(
            networkClient = get(),
            countryMapper = get()
        )
    }
}
