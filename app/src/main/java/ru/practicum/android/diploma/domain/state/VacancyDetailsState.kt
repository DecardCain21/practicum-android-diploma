package ru.practicum.android.diploma.domain.state

import ru.practicum.android.diploma.domain.models.VacancyDetails

data class VacancyDetailsState(
    val data: Data,
    val favorite: Favorite
) {

    sealed interface Data {
        data object Loading : Data
        data object Empty : Data
        data object Error : Data
        data object NoInternet : Data
        data class Payload(val details: VacancyDetails) : Data
    }

    sealed interface Favorite {
        data object InFavorite : Favorite
        data object NotInFavorite : Favorite
    }
}
