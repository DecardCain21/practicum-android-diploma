package ru.practicum.android.diploma.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.industry.IndustryRequest
import ru.practicum.android.diploma.data.dto.industry.IndustryResponse
import ru.practicum.android.diploma.data.mapper.IndustryMapper
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient.Companion.FAILED_INTERNET_CONNECTION_CODE
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient.Companion.SUCCESS_CODE
import ru.practicum.android.diploma.domain.api.IndustryRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

class IndustryRepositoryImpl(
    private val networkClient: NetworkClient,
    private val industryMapper: IndustryMapper,
) : IndustryRepository {

    override suspend fun getIndustries(): Resource<List<Industry>> {
        return withContext(Dispatchers.IO) {
            val response = networkClient.doRequest(IndustryRequest())
            when (response.resultCode) {
                FAILED_INTERNET_CONNECTION_CODE -> Resource.Error("-1")

                SUCCESS_CODE -> {
                    val data = industryMapper.map(response as IndustryResponse)
                    Resource.Success(data)
                }

                else -> Resource.Error("Server Error")
            }
        }
    }
}
