package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.VacancySearchResult
import ru.practicum.android.diploma.util.Resource

interface VacancyRepository {

    suspend fun searchVacancies(
        expression: String,
        page: Int,
        filter: Filter
    ): Resource<VacancySearchResult>

    suspend fun getVacancyDetails(id: String): Resource<VacancyDetails>

}
