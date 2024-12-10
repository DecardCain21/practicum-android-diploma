package ru.practicum.android.diploma.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.state.FavoriteVacanciesState
import ru.practicum.android.diploma.domain.usecase.favorite.GetAllFavoriteVacanciesUseCase

class FavoriteViewModel(
    private val getAllFavoriteVacanciesUseCase: GetAllFavoriteVacanciesUseCase
) : ViewModel() {

    private val _state = MutableLiveData<FavoriteVacanciesState>()
    val state: LiveData<FavoriteVacanciesState>
        get() = _state

    fun getFavoriteVacancies() = viewModelScope.launch {
        val vacancies = getAllFavoriteVacanciesUseCase.execute()
        val state = if (vacancies.isEmpty()) {
            FavoriteVacanciesState.Empty
        } else {
            FavoriteVacanciesState.Data(vacancies)
        }

        _state.postValue(state)
    }
}
