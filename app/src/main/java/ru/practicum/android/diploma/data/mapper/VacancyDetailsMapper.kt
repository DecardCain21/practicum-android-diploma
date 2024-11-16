package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.vacancy.details.VacancyDetailsDto
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsMapper {

    fun map(dto: VacancyDetailsDto) = VacancyDetails(
        id = dto.id,
        name = dto.name
    )

}
