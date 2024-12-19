package ru.practicum.android.diploma.ui.industry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient.Companion.FAILED_INTERNET_CONNECTION_CODE
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.state.IndustryState
import ru.practicum.android.diploma.domain.state.IndustryState.Industries
import ru.practicum.android.diploma.domain.state.IndustryState.Industries.Data
import ru.practicum.android.diploma.domain.state.IndustryState.Industries.Error
import ru.practicum.android.diploma.domain.state.IndustryState.Industries.Loading
import ru.practicum.android.diploma.domain.state.IndustryState.Industries.NoInternet
import ru.practicum.android.diploma.domain.state.IndustryState.Input.Empty
import ru.practicum.android.diploma.domain.usecase.GetIndustriesUseCase
import ru.practicum.android.diploma.domain.usecase.filters.tmp.GetTmpFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.tmp.SetTmpFiltersUseCase
import ru.practicum.android.diploma.util.debounce

class IndustryViewModel(
    private val getIndustriesUseCase: GetIndustriesUseCase,
    private val getTmpFiltersUseCase: GetTmpFiltersUseCase,
    private val setTmpFiltersUseCase: SetTmpFiltersUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<IndustryState> =
        MutableStateFlow(IndustryState(Empty, Loading))
    val state: StateFlow<IndustryState>
        get() = _state

    val searchDebounce: (String) -> Unit = debounce(
        delayMillis = 2_000L,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { changedText -> getIndustries(changedText) }

    fun getIndustries(sortExpression: String = "") = viewModelScope.launch(Dispatchers.Main) {
        val filters = getTmpFiltersUseCase.execute()
        val response = getIndustriesUseCase.execute()
        // как проверить, к примеру, тут
        val dataState = when {
            response.first?.isEmpty() == true -> Industries.Empty
            response.second?.isNotEmpty() == true -> {
                if (response.second == FAILED_INTERNET_CONNECTION_CODE.toString()) {
                    NoInternet
                } else {
                    Error
                }
            }

            else -> {
                val sortIndustry = searchFilter(response.first!!, sortExpression)
                if (sortIndustry.isEmpty()) Industries.Empty else Data(sortIndustry)
            }
        }

        _state.value = state.value.copy(data = dataState, selectedIndustry = filters.industry)
    }

    fun setFilters(industry: Industry?) {
        val filters = getTmpFiltersUseCase.execute()
            .copy(industry = industry)
        setTmpFiltersUseCase.execute(filters)

        _state.value = state.value.copy(selectedIndustry = industry)
    }

    private fun searchFilter(regions: List<Industry>, sortExpression: String) =
        regions.filter { it.name.lowercase().contains(sortExpression.lowercase()) }

    fun clearSearch() {
        getIndustries()
    }
}
