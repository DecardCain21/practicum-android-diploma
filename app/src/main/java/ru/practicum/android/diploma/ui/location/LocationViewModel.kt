package ru.practicum.android.diploma.ui.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.state.LocationState
import ru.practicum.android.diploma.domain.usecase.filters.location.ClearLocationUseCase
import ru.practicum.android.diploma.domain.usecase.filters.location.GetLocationUseCase
import ru.practicum.android.diploma.domain.usecase.filters.location.SetLocationUseCase
import ru.practicum.android.diploma.domain.usecase.filters.tmp.GetTmpFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.tmp.SetTmpFiltersUseCase

class LocationViewModel(
    private val getLocationUseCase: GetLocationUseCase,
    private val setLocationUseCase: SetLocationUseCase,
    private val clearLocationUseCase: ClearLocationUseCase,
    private val getTmpFiltersUseCase: GetTmpFiltersUseCase,
    private val setTmpFilterUseCase: SetTmpFiltersUseCase,
) : ViewModel() {
    init {
        val initLocationState =
            getTmpFiltersUseCase.execute().location
        if (initLocationState != null) {
            setLocationUseCase.execute(initLocationState)
        }
    }

    private val startLocationState =
        getTmpFiltersUseCase.execute().location?.let { LocationState.Data(it) } ?: LocationState.Empty


    private val _state: MutableStateFlow<LocationState> = MutableStateFlow(
        startLocationState
    )
    val state: StateFlow<LocationState>
        get() = _state

    fun getLocation() = viewModelScope.launch(Dispatchers.Main) {
        val location = getLocationUseCase.execute()
        val state = if (location.isEmpty) {
            LocationState.Empty
        } else {
            LocationState.Data(location)
        }

        _state.value = state
    }

    fun saveFilter() {
        val location = getLocationUseCase.execute()
        val filter = getTmpFiltersUseCase.execute()
            .copy(location = location)

        setTmpFilterUseCase.execute(filter)
        clearLocationUseCase.execute()
    }

    fun clearLocation() = clearLocationUseCase.execute()

    fun clearCountry() {
        clearLocationUseCase.execute()
        getLocation()
    }

    fun clearRegion() {
        val location = getLocationUseCase.execute()
            .copy(region = null)
        setLocationUseCase.execute(location)
    }
}
