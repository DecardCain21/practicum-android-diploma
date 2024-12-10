package ru.practicum.android.diploma.ui.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient.Companion.FAILED_INTERNET_CONNECTION_CODE
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Location
import ru.practicum.android.diploma.domain.state.CountryState
import ru.practicum.android.diploma.domain.state.CountryState.Loading
import ru.practicum.android.diploma.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.domain.usecase.filters.location.SetLocationUseCase

class CountryViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val setLocationUseCase: SetLocationUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<CountryState> = MutableStateFlow(Loading)
    val state: StateFlow<CountryState>
        get() = _state

    fun getCountries() = viewModelScope.launch {
        val countries = getCountriesUseCase.execute()
        val industryState = when {
            countries.first?.isEmpty() == true -> CountryState.Empty
            countries.second?.isNotEmpty() == true -> {
                if (countries.second == FAILED_INTERNET_CONNECTION_CODE.toString()) {
                    CountryState.NoInternet
                } else {
                    CountryState.Error
                }
            }

            else -> CountryState.Data(countries = countries.first!!)
        }
        _state.value = industryState
    }

    fun setCountry(country: Country) {
        val location = Location(country = country, region = null)
        setLocationUseCase.execute(location)
    }
}
