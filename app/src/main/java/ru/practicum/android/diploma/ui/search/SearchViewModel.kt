package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.state.VacancyState
import ru.practicum.android.diploma.domain.state.VacancyState.Input
import ru.practicum.android.diploma.domain.state.VacancyState.VacanciesList
import ru.practicum.android.diploma.domain.usecase.GetVacanciesUseCase
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val getVacanciesUseCase: GetVacanciesUseCase
) : ViewModel() {

    private val _state = MutableLiveData<VacancyState>()
    val state: LiveData<VacancyState> get() = _state

    private val searchDebounceAction = debounce<String>(
        delayMillis = SEARCH_DEBOUNCE_DELAY,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { changedText -> search(changedText) }

    fun search(expression: String) = viewModelScope.launch {
        val inputState = Input.Text(expression)
        _state.postValue(VacancyState(inputState, VacanciesList.Loading))

        val result = getVacanciesUseCase.execute(expression, page = 1)
        val vacanciesState: VacanciesList =
            if (result.first == null) {
                VacanciesList.Error
            } else if (result.first!!.isEmpty()) {
                VacanciesList.Empty
            } else {
                VacanciesList.Data(result.first!!)
            }
        _state.postValue(VacancyState(inputState, vacanciesState))
    }

    fun searchDebounce(expression: String) = searchDebounceAction(expression)

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
    }
}