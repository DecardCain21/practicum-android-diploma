package ru.practicum.android.diploma.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.ResponseSuccess
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.data.dto.area.AreaRequest
import ru.practicum.android.diploma.data.dto.area.AreaResponse
import ru.practicum.android.diploma.data.dto.industry.IndustryRequest
import ru.practicum.android.diploma.data.dto.industry.IndustryResponse
import ru.practicum.android.diploma.data.dto.vacancy.details.VacancyDetailsResponse
import ru.practicum.android.diploma.util.getConnected

abstract class RetrofitNetworkClient(
    private val headHunterApiService: HeadHunterApiService
) : NetworkClient {

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    override suspend fun doRequest(requestDto: Request): Response {
        if (!getConnected()) {
            return Response().apply { resultCode = FAILED_INTERNET_CONNECTION_CODE }
        }
        return withContext(Dispatchers.IO) {
            try {
                when (requestDto) {
                    is VacancySearchRequest -> {
                        Log.e("responseCode", "searchVacancies:200?")
                        headHunterApiService.searchVacancies(
                            options = requestDto.options
                        ).apply { resultCode = SUCCESS_CODE }
                    }

                    is VacancyDetailsRequest -> getVacancyDetails(requestDto)
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

    /*@Suppress("TooGenericExceptionCaught", "SwallowedException")
    override suspend fun doRequest(requestDto: Request): Response {
        if (!getConnected()) {
            return NetworkError.NoInternet()
        }
        return withContext(Dispatchers.IO) {
            try {
                when (requestDto) {
                    is VacancySearchRequest -> headHunterApiService.searchVacancies(
                        options = requestDto.options
                    ).apply { ResponseSuccess() }

                    is VacancyDetailsRequest -> getVacancyDetails(requestDto)
                    is IndustryRequest -> getIndustries()
                    is AreaRequest -> getArea()
                    else -> NetworkError.BadCode("${requestDto.javaClass}", BAD_REQUEST_CODE)
                }
            } catch (e: HttpException) {
                if (e.code() == NOT_FOUND_CODE) {
                    NetworkError.NoData("${requestDto.javaClass}")
                } else {
                    NetworkError.ServerError("${requestDto.javaClass}", "${e.code()}")
                }
            }
        }
    }*/

    /*private suspend fun searchVacancies(
        input: VacancySearchRequest
    ): VacanciesSearchResponse {
        return headHunterApiService.searchVacancies(
            options = input.options
        ).apply { ResponseSuccess() }
    }*/

    private suspend fun getVacancyDetails(
        input: VacancyDetailsRequest
    ): VacancyDetailsResponse = headHunterApiService.getVacancyDetails(
        vacancyId = input.id
    ).apply { ResponseSuccess() }

    private suspend fun getIndustries(): IndustryResponse {
        val result = headHunterApiService.getIndustries()
        return IndustryResponse(industries = result)
            .apply { ResponseSuccess() }
    }

    private suspend fun getArea(): AreaResponse {
        val result = headHunterApiService.getArea()
        return AreaResponse(area = result)
            .apply { ResponseSuccess() }
    }

    companion object {
        const val FAILED_INTERNET_CONNECTION_CODE = -1
        const val SUCCESS_CODE = 200
        const val BAD_REQUEST_CODE = 400
        const val NOT_FOUND_CODE = 404
        const val SERVER_ERROR_CODE = 500
    }
}
