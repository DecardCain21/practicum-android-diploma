package ru.practicum.android.diploma.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.util.Resource

class GetIndustriesUseCase(
    private val repository: IndustryRepository
) {
    suspend fun execute() = withContext(Dispatchers.IO) {
        when (val result = repository.getIndustries()) {
            is Resource.Success -> Pair(result.data, null)
            is Resource.Error -> Pair(null, result.message)
        }
    }
}
