package ru.practicum.android.diploma.domain.state

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface FavoriteVacanciesState {
    data object Empty : FavoriteVacanciesState
    data object Error : FavoriteVacanciesState
    data class Data(val vacancies: List<Vacancy>) : FavoriteVacanciesState
}
