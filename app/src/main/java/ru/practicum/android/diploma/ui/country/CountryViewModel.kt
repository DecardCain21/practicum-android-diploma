package ru.practicum.android.diploma.ui.country

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.state.CountryState
import ru.practicum.android.diploma.domain.state.CountryState.Loading
import ru.practicum.android.diploma.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.domain.usecase.filters.GetFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.SetFiltersUseCase

class CountryViewModel(
    context: Context,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getFiltersUseCase: GetFiltersUseCase,
    private val setFiltersUseCase: SetFiltersUseCase
) : ViewModel() {

    private val otherAreas = context.getString(R.string.other_areas)

    private val _state: MutableStateFlow<CountryState> = MutableStateFlow(Loading)
    val state: StateFlow<CountryState>
        get() = _state

    fun getCountries() = viewModelScope.launch {
        val countriesResult = getCountriesUseCase.execute()
        val countries = countriesResult.first.orEmpty()
        val errorState = countriesResult.second

        _state.value = when {
            countries.isEmpty() -> CountryState.Empty
            !errorState.isNullOrEmpty() -> CountryState.Error
            else -> CountryState.Data(countries = moveCountryToEnd(countries, otherAreas))
        }
    }

    fun setFilter(country: Country) {
        val filters = getFiltersUseCase.execute().copy(area = country)
        setFiltersUseCase.execute(filters)
    }

    private fun moveCountryToEnd(countries: List<Country>, countryName: String): List<Country> {
        val countryToMove = countries.find { it.name == countryName }
        return if (countryToMove != null) {
            countries.filter { it != countryToMove } + countryToMove
        } else {
            countries
        }
    }
}
