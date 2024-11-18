package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.favorite.FavoriteViewModel
import ru.practicum.android.diploma.ui.search.SearchViewModel
import ru.practicum.android.diploma.ui.vacancy.VacancyViewModel

val viewModelModule = module {

    viewModel<SearchViewModel> {
        SearchViewModel(get())
    }

    viewModel<VacancyViewModel> {
        VacancyViewModel(get(), get())
    }

    viewModel {
        FavoriteViewModel()
    }
}
