package ru.practicum.android.diploma.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.data.dto.vacancy.details.VacancyDetailsDto
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
                    else -> Response().apply { resultCode = BAD_REQUEST_CODE }
                }
            } catch (e: HttpException) {
                Response().apply { resultCode = SERVER_ERROR_CODE }
            }
        }
    }

    private suspend fun searchVacancies(
        input: VacancySearchRequest
    ): VacanciesSearchResponse = headHunterApiService.searchVacancies(
        text = input.expression,
        page = input.page,
        perPage = input.perPage
    ).apply { resultCode = SUCCESS_CODE }

    private suspend fun getVacancyDetails(
        input: VacancyDetailsRequest
    ): VacancyDetailsDto = headHunterApiService.getVacancyDetails(
        vacancyId = input.id
    ).apply { resultCode = SUCCESS_CODE }

    companion object {
        const val FAILED_INTERNET_CONNECTION_CODE = -1
        const val SUCCESS_CODE = 200
        const val BAD_REQUEST_CODE = 400
        const val SERVER_ERROR_CODE = 500
    }
}
