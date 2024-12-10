package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.common.AppConstants.EMPTY_INT_PARAM_VALUE
import ru.practicum.android.diploma.data.dto.vacancy.details.VacancyDetailsResponse
import ru.practicum.android.diploma.data.formatter.AddressFormatter
import ru.practicum.android.diploma.data.formatter.SkillsFormatter
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsMapper {

    fun map(dto: VacancyDetailsResponse) = VacancyDetails(
        id = dto.id,
        imageUrl = dto.employer?.logoUrls?.original,
        name = dto.name.orEmpty(),
        area = dto.area.name.orEmpty(),
        salaryFrom = dto.salary?.from ?: EMPTY_INT_PARAM_VALUE,
        salaryTo = dto.salary?.to ?: EMPTY_INT_PARAM_VALUE,
        currency = dto.salary?.currency.orEmpty(),
        employerName = dto.employer?.name.orEmpty(),
        experience = dto.experience?.name.orEmpty(),
        employmentName = dto.employment?.name.orEmpty(),
        schedule = dto.schedule.name.orEmpty(),
        description = dto.description.orEmpty(),
        descriptionSkills = SkillsFormatter.format(dto.keySkills),
        url = dto.alternateUrl.orEmpty(),
        address = AddressFormatter.format(dto.address)
    )
}
