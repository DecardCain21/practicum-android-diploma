package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.data.dto.vacancy.details.VacancyDetailsDto

interface HeadHunterApiService {

    @GET("/vacancies")
    suspend fun searchVacancies(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): VacanciesSearchResponse

    @GET("/vacancies/{vacancyId}")
    suspend fun getVacancyDetails(
        @Path("vacancyId") vacancyId: String
    ): VacancyDetailsDto

}
