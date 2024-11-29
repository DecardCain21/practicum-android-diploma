package ru.practicum.android.diploma.ui.region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.AppConstants.SEARCH_DEBOUNCE_DELAY
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.domain.state.RegionState
import ru.practicum.android.diploma.domain.state.RegionState.Data
import ru.practicum.android.diploma.domain.state.RegionState.Input
import ru.practicum.android.diploma.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.domain.usecase.filters.GetFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.SetFiltersUseCase
import ru.practicum.android.diploma.util.debounce

class RegionViewModel(
    private val getFiltersUseCase: GetFiltersUseCase,
    private val setFiltersUseCase: SetFiltersUseCase,
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    private var lastExpression = ""

    private val _state: MutableStateFlow<RegionState> =
        MutableStateFlow(RegionState(Input.Empty, Data.Loading))
    val state: StateFlow<RegionState>
        get() = _state

    fun getRegions(sortExpression: String = "") = viewModelScope.launch(Dispatchers.Main) {
        val countries = getCountriesUseCase.execute()
        val dataState = when {
            countries.first?.isEmpty() == true -> Data.Empty
            countries.second?.isNotEmpty() == true -> Data.Error
            else -> {
                val sortedRegions = sortRegionsIfNeeded(parseRegions(countries.first!!), sortExpression)
                if (sortedRegions.isEmpty()) Data.Empty else Data.Data(sortedRegions)
            }
        }
        _state.value = state.value.copy(data = dataState)
    }

    private val searchDebounceAction = debounce<String>(
        delayMillis = SEARCH_DEBOUNCE_DELAY,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { changedText ->
        if (changedText != lastExpression) {
            _state.value = state.value.copy(data = Data.Loading, input = Input.Text(changedText))
            getRegions(changedText)
        }
    }

    private fun sortRegionsIfNeeded(regions: List<Region>, sortExpression: String): List<Region> {
        return if (sortExpression.isNotBlank()) {
            regions.filter { it.name.contains(sortExpression, ignoreCase = true) }
                .sortedBy { it.name }
        } else {
            regions
        }
    }

    fun clearRegion() {
        _state.value = state.value.copy(data = Data.Loading, input = Input.Empty)
        getRegions()
    }

    fun sortDebounce(expression: String) {
        if (expression.isBlank()) return
        searchDebounceAction(expression)
    }

    fun setFilter(region: Region) {
        val filters = getFiltersUseCase.execute()
            .copy(region = region)
        setFiltersUseCase.execute(filters)
    }

    private fun parseRegions(countries: List<Country>): List<Region> {
        val filter = getFiltersUseCase.execute()
        val regions = if (filter.area == null) {
            countries.flatMap { it.regions }
        } else {
            filter.area.regions
        }
        return regions
    }
}
