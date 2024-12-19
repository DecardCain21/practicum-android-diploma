package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.NetworkError
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient.Companion.FAILED_INTERNET_CONNECTION_CODE
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.state.VacancyState
import ru.practicum.android.diploma.domain.state.VacancyState.Input
import ru.practicum.android.diploma.domain.state.VacancyState.VacanciesList
import ru.practicum.android.diploma.domain.usecase.filters.search.GetSearchFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.search.SetSearchFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.filters.tmp.GetTmpFiltersUseCase
import ru.practicum.android.diploma.domain.usecase.vacancy.GetVacanciesUseCase
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val getTmpFiltersUseCase: GetTmpFiltersUseCase,
    private val getSearchFiltersUseCase: GetSearchFiltersUseCase,
    private val setSearchFiltersUseCase: SetSearchFiltersUseCase
) : ViewModel() {

    private var lastExpression = ""
    private var currentPage = 1
    private var maxPage = 0

    val isNotLastPage: Boolean
        get() = currentPage < maxPage

    private var isNextPageLoading = false
    private var lastFilter: Filter? = null

    private val _state: MutableStateFlow<VacancyState> = MutableStateFlow(
        VacancyState(Input.Empty, VacanciesList.Start)
    )
    val state: StateFlow<VacancyState>
        get() = _state

    private val searchDebounceAction = debounce<String>(
        delayMillis = 2_000L,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { changedText ->
        if (changedText != lastExpression && changedText.isNotBlank()) {
            search(changedText)
        }
        if (!compareTmpAndSearchFilters() && changedText == lastExpression && changedText.isNotBlank()) {
            search(lastExpression)
        }
    }

    private fun getFilter() = getTmpFiltersUseCase.execute()

    fun isFilterApplied() = getFilter() != Filter.empty

    fun clearSearch() {
        lastExpression = ""
        _state.value = VacancyState(Input.Empty, VacanciesList.Start)
    }

    private fun search(expression: String) {
        lastExpression = expression
        _state.value = VacancyState(
            input = Input.Text(lastExpression),
            vacanciesList = VacanciesList.Empty,
        )
        setSearchFiltersUseCase.execute(getTmpFiltersUseCase.execute())
        _state.value = VacancyState(Input.Text(expression), VacanciesList.Loading)
        requestToServer(expression)
    }

    private fun requestToServer(expression: String) = viewModelScope.launch {
        isNextPageLoading = true
        val filter = getSearchFiltersUseCase.execute()
        lastFilter = filter
        val result = getVacanciesUseCase.execute(
            expression = expression,
            page = currentPage,
            filter = filter
        )
        val vacancyState: VacancyState = when (result.networkError) {
            is NetworkError.BadCode -> state.value.copy(vacanciesList = VacanciesList.Error)
            is NetworkError.NoData -> state.value.copy(vacanciesList = VacanciesList.Empty)
            is NetworkError.NoInternet -> state.value.copy(vacanciesList = VacanciesList.NoInternet)
            is NetworkError.ServerError -> state.value.copy(vacanciesList = VacanciesList.Error)
            else -> {
                isNextPageLoading = false
                currentPage += 1
                result.data.let { maxPage = it?.pages ?: 0 }
                val totalVacancyCount = result.data?.totalVacancyCount
                state.value.copy(
                    vacanciesList = VacanciesList.Data(
                        result.data?.items ?: emptyList(),
                        totalVacancyCount ?: 0
                    )
                )
            }
        }
        /*val vacancyState: VacancyState = when {
            resultData == null -> {
                if (result.second == FAILED_INTERNET_CONNECTION_CODE.toString()) {
                    state.value.copy(vacanciesList = VacanciesList.NoInternet)
                } else {
                    state.value.copy(vacanciesList = VacanciesList.Error)
                }
            }

            resultData.isEmpty() -> state.value.copy(vacanciesList = VacanciesList.Empty)

            else -> {
                isNextPageLoading = false
                currentPage += 1
                result.first?.let { maxPage = it.pages }
                state.value.copy(vacanciesList = VacanciesList.Data(resultData, totalVacancyCount))
            }
        }*/
        _state.value = vacancyState
    }

    fun searchDebounce(expression: String) {
        if (expression.isBlank()) clearSearch()
        searchDebounceAction(expression)
    }

    fun onLastItemReached() {
        if (!isNextPageLoading) requestToServer(lastExpression)
    }

    private fun compareTmpAndSearchFilters(): Boolean {
        val tmpFilters = lastFilter
        val searchFilters = getSearchFiltersUseCase.execute()
        return tmpFilters == searchFilters
    }
}
