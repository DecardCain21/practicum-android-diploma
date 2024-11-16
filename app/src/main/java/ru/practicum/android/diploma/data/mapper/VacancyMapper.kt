package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.VacanciesSearchResponse
import ru.practicum.android.diploma.data.dto.vacancy.self.VacancyDto
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyMapper {

    fun map(response: VacanciesSearchResponse): List<Vacancy> =
        response.items.map { vacancy -> map(vacancy) }

    private fun map(dto: VacancyDto) = Vacancy(
        id = dto.id,
        imageUrl = dto.employer.logoUrls?.original,
        name = dto.name,
        city = getValueOrDefault(dto.area?.name, EMPTY_PARAM_VALUE),
        salaryFrom = getValueOrDefault(dto.salary?.from?.toString(), EMPTY_PARAM_VALUE),
        salaryTo = getValueOrDefault(dto.salary?.to?.toString(), EMPTY_PARAM_VALUE),
        currency = getValueOrDefault(dto.salary?.currency, EMPTY_PARAM_VALUE),
        employerName = dto.employer.name,
        experience = getValueOrDefault(dto.experience?.name, EMPTY_PARAM_VALUE),
        employmentName = getValueOrDefault(dto.employment?.name, EMPTY_PARAM_VALUE),
        schedule = getValueOrDefault(dto.schedule?.name, EMPTY_PARAM_VALUE),
        descriptionResponsibility = getValueOrDefault(dto.snippet?.responsibility, EMPTY_PARAM_VALUE),
        descriptionRequirement = getValueOrDefault(dto.snippet?.requirement, EMPTY_PARAM_VALUE),
        descriptionConditions = "???", // Может быть заполнено позже
        descriptionSkills = "???" // Может быть заполнено позже
    )

    private fun <T> getValueOrDefault(
        value: T?,
        defaultValue: String
    ): String = value?.toString() ?: defaultValue

    companion object {
        const val EMPTY_PARAM_VALUE = "Не указано"
    }
}
