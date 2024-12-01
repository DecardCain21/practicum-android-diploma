package ru.practicum.android.diploma.di

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
        SearchViewModel(
            getVacanciesUseCase = get(),
            getSearchFiltersUseCase = get()
        )
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
        LocationViewModel(
            getFiltersUseCase = get(),
            setFiltersUseCase = get()
        )
    }

    viewModel {
        FiltersViewModel(
            setFiltersUseCase = get(),
            getFiltersUseCase = get(),
            clearFiltersUseCase = get(),
            setSearchFiltersUseCase = get()
        )
    }

    viewModel {
        CountryViewModel(
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
        RegionViewModel(
            getFiltersUseCase = get(),
            setFiltersUseCase = get(),
            getCountriesUseCase = get()
        )
    }
}
