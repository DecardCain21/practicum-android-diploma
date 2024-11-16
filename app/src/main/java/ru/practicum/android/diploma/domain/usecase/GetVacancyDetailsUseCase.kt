package ru.practicum.android.diploma.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.util.Resource

class GetVacancyDetailsUseCase(
    private val repository: VacancyRepository
) {

    suspend fun execute(id: String) = withContext(Dispatchers.IO) {
        when (val result = repository.getVacancyDetails(id)) {
            is Resource.Success -> Pair(result.data, null)
            is Resource.Error -> Pair(null, result.message)
        }
    }
}
