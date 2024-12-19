package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.data.dto.vacancy.self.VacancyDto
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancySearchResult

class VacancyMapper {

    fun map(response: VacanciesSearchResponse) = VacancySearchResult(
        items = response.items.map { vacancy -> map(vacancy) },
        pages = response.pages,
        totalVacancyCount = response.found,
    )

    private fun map(dto: VacancyDto) = Vacancy(
        id = dto.id,
        imageUrl = dto.employer.logoUrls?.original,
        name = dto.name,
        city = dto.area?.name.orEmpty(),
        salaryFrom = dto.salary?.from ?: 0,
        salaryTo = dto.salary?.to ?: 0,
        currency = dto.salary?.currency.orEmpty(),
        employerName = dto.employer.name.orEmpty(),
        experience = dto.experience?.name.orEmpty(),
        employmentName = dto.employment?.name.orEmpty(),
        schedule = dto.schedule?.name.orEmpty()
    )
}
