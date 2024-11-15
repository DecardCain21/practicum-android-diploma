package ru.practicum.android.diploma.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.state.VacancyState
import ru.practicum.android.diploma.domain.state.VacancyState.Input
import ru.practicum.android.diploma.domain.state.VacancyState.VacanciesList
import ru.practicum.android.diploma.domain.usecase.GetVacanciesUseCase
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val getVacanciesUseCase: GetVacanciesUseCase
) : ViewModel() {

    private var currentPage = 1
    private var maxPage = 2
    private val vacanciesList = mutableListOf<Vacancy>()
    private var lastExpression = ""

    private var isNextPageLoading = false

    private val _state = MutableLiveData<VacancyState>()
    val state: LiveData<VacancyState> get() = _state

    private val searchDebounceAction = debounce<String>(
        delayMillis = SEARCH_DEBOUNCE_DELAY,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { changedText -> search(changedText) }

    private fun search(expression: String) = viewModelScope.launch {
        isNextPageLoading = true
        lastExpression = expression
        val inputState = Input.Text(expression)
        _state.postValue(VacancyState(inputState, VacanciesList.Loading))

        val result = getVacanciesUseCase.execute(expression, page = currentPage)
        val resultData = result.first
        val vacanciesState: VacanciesList =
            when (val loadedVacanciesList = resultData?.items) {
                null -> {
                    if (result.second == RetrofitNetworkClient.FAILED_INTERNET_CONNECTION_CODE.toString()) {
                        VacanciesList.NoInternet
                    } else {
                        VacanciesList.Error
                    }
                }

                emptyList<Vacancy>() -> {
                    if (vacanciesList.isEmpty()) VacanciesList.Empty else VacanciesList.Data(vacanciesList)
                }

                else -> {
                        VacanciesList.Data(loadedVacanciesList).also {
                            isNextPageLoading = false
                            currentPage++
                            maxPage = resultData.pages
                            vacanciesList.addAll(loadedVacanciesList)
                        }
                }
            }
        _state.postValue(VacancyState(inputState, vacanciesState))
    }

    fun onLastItemReached() {
        if (!isNextPageLoading && currentPage < maxPage) search(lastExpression)
    }

    fun searchDebounce(expression: String) = searchDebounceAction(expression)

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
    }
}
