package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.country.CountryViewModel
import ru.practicum.android.diploma.ui.favorite.FavoriteViewModel
import ru.practicum.android.diploma.ui.filters.FiltersViewModel
import ru.practicum.android.diploma.ui.industry.IndustryViewModel
import ru.practicum.android.diploma.ui.location.LocationViewModel
import ru.practicum.android.diploma.ui.region.RegionViewModel
import ru.practicum.android.diploma.ui.search.SearchViewModel
import ru.practicum.android.diploma.ui.vacancy.VacancyViewModel

val viewModelModule = module {

    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        VacancyViewModel(
            getVacancyDetailsUseCase = get(),
            getFavoriteVacancyByIdUseCase = get(),
            addVacancyToFavoriteUseCase = get(),
            deleteVacancyFromFavoriteUseCase = get(),
            sharingInteract = get()
        )
    }

    viewModel {
        FavoriteViewModel(
            getAllFavoriteVacanciesUseCase = get()
        )
    }

    viewModel {
        LocationViewModel(get())
    }

    viewModel {
        FiltersViewModel(get(), get(), get())
    }

    viewModel {
        CountryViewModel(
            context = androidContext(),
            getCountriesUseCase = get(),
            getFiltersUseCase = get(),
            setFiltersUseCase = get()
        )
    }

    viewModel {
        IndustryViewModel(
            getIndustriesUseCase = get(),
            getFiltersUseCase = get(),
            setFiltersUseCase = get()
        )
    }

    viewModel {
        RegionViewModel(get(), get(), get())
    }
}
