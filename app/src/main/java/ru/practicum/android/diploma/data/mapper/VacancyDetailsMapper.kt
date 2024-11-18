package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.vacancy.details.VacancyDetailsDto
import ru.practicum.android.diploma.data.dto.vacancy.nested.KeySkillDto
import ru.practicum.android.diploma.domain.models.VacancyDetails
import kotlin.text.Typography.middleDot

class VacancyDetailsMapper {
    fun map(dto: VacancyDetailsDto) = VacancyDetails(
        id = dto.id,
        imageUrl = dto.employer?.logoUrls?.original,
        name = dto.name ?: EMPTY_PARAM_VALUE,
        city = dto.area.name ?: EMPTY_PARAM_VALUE,
        salaryFrom = dto.salary?.from ?: EMPTY_INT_PARAM_VALUE,
        salaryTo = dto.salary?.to ?: EMPTY_INT_PARAM_VALUE,
        currency = dto.salary?.currency ?: EMPTY_PARAM_VALUE,
        employerName = dto.employer?.name ?: EMPTY_PARAM_VALUE,
        experience = dto.experience?.name ?: EMPTY_PARAM_VALUE,
        employmentName = dto.employment?.name ?: EMPTY_PARAM_VALUE,
        schedule = dto.schedule.name ?: EMPTY_PARAM_VALUE,
        description = dto.description ?: EMPTY_PARAM_VALUE,
        descriptionSkills = skillsFormat(dto.keySkills),
        url = dto.alternateUrl ?: EMPTY_PARAM_VALUE
    )

    private fun skillsFormat(list: List<KeySkillDto>): String {
        return list.mapIndexed { _, keySkillDto -> "$middleDot  ${keySkillDto.name}" }.joinToString("\n")
    }

    companion object {
        const val EMPTY_PARAM_VALUE = ""
        const val EMPTY_INT_PARAM_VALUE = 0
    }
}
