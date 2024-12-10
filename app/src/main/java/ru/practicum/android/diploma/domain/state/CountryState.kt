package ru.practicum.android.diploma.domain.state

import ru.practicum.android.diploma.domain.models.Country

sealed interface CountryState {
    data object NoInternet : CountryState
    data object Loading : CountryState
    data object Empty : CountryState
    data object Error : CountryState
    data class Data(val countries: List<Country>) : CountryState
}
