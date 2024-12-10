package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.filter.FilterDto
import ru.practicum.android.diploma.domain.models.Filter

class FilterMapper(
    private val industryMapper: IndustryMapper,
    private val locationMapper: LocationMapper
) {

    fun map(dto: FilterDto) = Filter(
        location = dto.location?.let { locationMapper.map(it) },
        industry = dto.industry?.let { industryMapper.map(it) },
        salary = dto.salary,
        withoutSalaryButton = dto.withoutSalaryButton
    )

    fun map(filter: Filter) = FilterDto(
        location = filter.location?.let { locationMapper.map(it) },
        industry = filter.industry?.let { industryMapper.map(it) },
        salary = filter.salary,
        withoutSalaryButton = filter.withoutSalaryButton
    )
}
