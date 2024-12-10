package ru.practicum.android.diploma.domain.usecase.favorite

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.FavoriteVacanciesRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails

class AddVacancyToFavoriteUseCase(
    private val repository: FavoriteVacanciesRepository
) {

    suspend fun execute(vacancy: VacancyDetails) =
        withContext(Dispatchers.IO) { repository.add(vacancy) }

}
