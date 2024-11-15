package ru.practicum.android.diploma.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.data.mapper.VacancyResponseMapper
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.util.Resource

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val mapper: VacancyResponseMapper
) : VacancyRepository {

    override suspend fun searchVacancy(expression: String, page: Int): Resource<VacancySearchResult> {
        return withContext(Dispatchers.IO) {
            val response = networkClient.doRequest(VacancySearchRequest(expression, page))
            when (response.resultCode) {
                RetrofitNetworkClient.FAILED_INTERNET_CONNECTION_CODE -> {
                    Resource.Error("-1")
                }

                RetrofitNetworkClient.SUCCESS_CODE -> {
                    Log.e(VacancyRepository::class.simpleName, response.toString())
                    val data = mapper.map(response as VacancySearchResponse)
                    Resource.Success(data)
                }

                else -> {
                    Resource.Error("Server Error")
                }
            }
        }
    }
}
