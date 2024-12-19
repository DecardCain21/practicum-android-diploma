package ru.practicum.android.diploma.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.area.AreaRequest
import ru.practicum.android.diploma.data.dto.area.AreaResponse
import ru.practicum.android.diploma.data.mapper.CountryMapper
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.NetworkError
import ru.practicum.android.diploma.data.network.NetworkError.Companion.BAD_REQUEST_CODE
import ru.practicum.android.diploma.data.network.NetworkError.Companion.FAILED_INTERNET_CONNECTION_CODE
import ru.practicum.android.diploma.data.network.NetworkError.Companion.SUCCESS_CODE
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
                // Возвращаем сообщение, как проверить на UI
                FAILED_INTERNET_CONNECTION_CODE -> Resource.Error(
                    message = NetworkError
                        .NoInternet()
                        .javaClass.name
                )

                BAD_REQUEST_CODE -> Resource.Error(
                    message = NetworkError.BadCode(
                        response.javaClass.name,
                        code = response.resultCode
                    ).javaClass.name
                )

                SUCCESS_CODE -> {
                    val data = countryMapper.map(response as AreaResponse)
                    Resource.Success(data)
                }

                else -> Resource.Error()
            }
        }
    }
}
