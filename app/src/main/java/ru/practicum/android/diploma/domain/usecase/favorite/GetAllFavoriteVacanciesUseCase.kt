package ru.practicum.android.diploma.domain.usecase.favorite

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.FavoriteVacanciesRepository

class GetAllFavoriteVacanciesUseCase(
    private val repository: FavoriteVacanciesRepository
) {

    suspend fun execute() = withContext(Dispatchers.IO) { repository.getAll() }

}
