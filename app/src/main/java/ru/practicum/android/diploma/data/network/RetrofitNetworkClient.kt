package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.industry.IndustryRequest
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.data.dto.area.AreaRequest
import ru.practicum.android.diploma.data.dto.area.AreaResponse
import ru.practicum.android.diploma.data.dto.industry.IndustryResponse
import ru.practicum.android.diploma.data.dto.vacancy.details.VacancyDetailsResponse
import ru.practicum.android.diploma.util.getConnected

class RetrofitNetworkClient(
    private val headHunterApiService: HeadHunterApiService
) : NetworkClient {

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    override suspend fun doRequest(dto: Any): Response {
        if (!getConnected()) {
            return Response().apply { resultCode = FAILED_INTERNET_CONNECTION_CODE }
        }
        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is VacancySearchRequest -> searchVacancies(dto)
                    is VacancyDetailsRequest -> getVacancyDetails(dto)
                    is IndustryRequest -> getIndustries()
                    is AreaRequest -> getArea()
                    else -> Response().apply { resultCode = BAD_REQUEST_CODE }
                }
            } catch (e: HttpException) {
                val code = if (e.code() == NOT_FOUND_CODE) NOT_FOUND_CODE else SERVER_ERROR_CODE
                Response().apply { resultCode = code }
            }
        }
    }

    private suspend fun searchVacancies(
        input: VacancySearchRequest
    ): VacanciesSearchResponse {
        return headHunterApiService.searchVacancies(
            options = input.options
        ).apply { resultCode = SUCCESS_CODE }
    }

    private suspend fun getVacancyDetails(
        input: VacancyDetailsRequest
    ): VacancyDetailsResponse = headHunterApiService.getVacancyDetails(
        vacancyId = input.id
    ).apply { resultCode = SUCCESS_CODE }

    private suspend fun getIndustries(): IndustryResponse {
        val result = headHunterApiService.getIndustries()
        return IndustryResponse(industries = result)
            .apply { resultCode = SUCCESS_CODE }
    }

    private suspend fun getArea(): AreaResponse {
        val result = headHunterApiService.getArea()
        return AreaResponse(area = result)
            .apply { resultCode = SUCCESS_CODE }
    }

    companion object {
        const val FAILED_INTERNET_CONNECTION_CODE = -1
        const val SUCCESS_CODE = 200
        const val BAD_REQUEST_CODE = 400
        const val NOT_FOUND_CODE = 404
        const val SERVER_ERROR_CODE = 500
    }
}
