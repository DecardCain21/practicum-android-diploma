package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.Resource

interface VacancyRepository {

    suspend fun searchVacancies(expression: String, page: Int): Resource<List<Vacancy>>

    suspend fun getVacancyDetails(id: String): Resource<VacancyDetails>

}
