package ru.practicum.android.diploma.ui.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.state.CountryState
import ru.practicum.android.diploma.domain.state.CountryState.Loading
import ru.practicum.android.diploma.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.domain.usecase.filters.GetFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.SetFiltersUseCase

class CountryViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getFiltersUseCase: GetFiltersUseCase,
    private val setFiltersUseCase: SetFiltersUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CountryState> = MutableStateFlow(Loading)
    val state: StateFlow<CountryState>
        get() = _state

    fun getCountries() = viewModelScope.launch {
        val countries = getCountriesUseCase.execute()
        val industryState = when {
            countries.first?.isEmpty() == true -> CountryState.Empty
            countries.second?.isNotEmpty() == true -> CountryState.Error
            else -> CountryState.Data(countries = countries.first!!)
        }
        _state.value = industryState
    }

    fun setFilter(country: Country) {
        val filters = getFiltersUseCase.execute().copy(country = country)
        setFiltersUseCase.execute(filters)
    }
}
