package ru.practicum.android.diploma.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.area.AreaRequest
import ru.practicum.android.diploma.data.dto.area.AreaResponse
import ru.practicum.android.diploma.data.mapper.CountryMapper
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient.Companion.FAILED_INTERNET_CONNECTION_CODE
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient.Companion.SUCCESS_CODE
import ru.practicum.android.diploma.domain.api.CountryRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.Resource

class CountryRepositoryImpl(
    private val networkClient: NetworkClient,
    private val countryMapper: CountryMapper,
) : CountryRepository {
    override suspend fun getCountry(): Resource<List<Country>> {
        return withContext(Dispatchers.IO) {
            val response = networkClient.doRequest(AreaRequest())
            when (response.resultCode) {
                FAILED_INTERNET_CONNECTION_CODE -> Resource.Error("-1")

                SUCCESS_CODE -> {
                    val data = countryMapper.map(response as AreaResponse)
                    Resource.Success(data)
                }

                else -> Resource.Error("Server Error")
            }
        }
    }
}
