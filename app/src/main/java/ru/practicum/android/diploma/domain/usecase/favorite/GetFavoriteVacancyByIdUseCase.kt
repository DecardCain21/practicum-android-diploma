package ru.practicum.android.diploma.domain.usecase.favorite

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.FavoriteVacanciesRepository

class GetFavoriteVacancyByIdUseCase(
    private val repository: FavoriteVacanciesRepository
) {

    suspend fun execute(id: String) =
        withContext(Dispatchers.IO) { repository.getById(id) }

}
