package ru.practicum.android.diploma.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.util.Resource

class GetIndustriesUseCase(
    private val repository: IndustryRepository
) {
    suspend fun execute() = withContext(Dispatchers.IO) {
        // Если возвращаем не Pair тогда как обработать отдельно NoInternet и Error
        when (val result = repository.getIndustries()) {
            is Resource.Success -> Pair(result.data, null)
            is Resource.Error -> Pair(null, result.message)
        }
    }
}
