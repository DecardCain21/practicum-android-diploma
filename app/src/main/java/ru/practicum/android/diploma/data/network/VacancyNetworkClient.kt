package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.data.dto.VacancySearchRequest

class VacancyNetworkClient(headHunterApiService: HeadHunterApiService) : RetrofitNetworkClient(headHunterApiService) {
    override suspend fun doRequest(requestDto: Request): Response {
        return super.doRequest(requestDto)
    }

    suspend fun execute(
        input: VacancySearchRequest
    ): VacanciesSearchResponse {
        return withContext(Dispatchers.IO) {
            doRequest(requestDto = input) as VacanciesSearchResponse
        }
    }
}
