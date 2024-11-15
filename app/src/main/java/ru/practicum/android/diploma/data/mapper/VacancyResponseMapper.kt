package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.data.dto.vacancy.ItemDto
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancySearchResult

class VacancyResponseMapper {

    fun map(response: VacancySearchResponse): VacancySearchResult {
        val vacanciesList: List<Vacancy> = response.items.map { vacancy -> createVacancy(vacancy) }
        val pages = response.pages
        return VacancySearchResult(
            items = vacanciesList,
            pages = pages
        )
    }

    private fun createVacancy(vacancy: ItemDto) = Vacancy(
        id = vacancy.id,
        imageUrl = vacancy.employer.logoUrls?.original,
        name = vacancy.name,
        city = getValueOrDefault(vacancy.area?.name, EMPTY_PARAM_VALUE),
        salaryFrom = getValueOrDefault(vacancy.salary?.from?.toString(), EMPTY_PARAM_VALUE),
        salaryTo = getValueOrDefault(vacancy.salary?.to?.toString(), EMPTY_PARAM_VALUE),
        currency = getValueOrDefault(vacancy.salary?.currency, EMPTY_PARAM_VALUE),
        employerName = vacancy.employer.name,
        experience = getValueOrDefault(vacancy.experience?.name, EMPTY_PARAM_VALUE),
        employmentName = getValueOrDefault(vacancy.employment?.name, EMPTY_PARAM_VALUE),
        schedule = getValueOrDefault(vacancy.schedule?.name, EMPTY_PARAM_VALUE),
        descriptionResponsibility = getValueOrDefault(vacancy.snippet.responsibility, EMPTY_PARAM_VALUE),
        descriptionRequirement = getValueOrDefault(vacancy.snippet.requirement, EMPTY_PARAM_VALUE),
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
