package ru.practicum.android.diploma.data.dto.filter

import ru.practicum.android.diploma.data.dto.industry.IndustryDto

data class FilterDto(
    val location: LocationDto?,
    val industry: IndustryDto?,
    val salary: Int?,
    val withoutSalaryButton: Boolean
) {

    companion object {
        val empty = FilterDto(
            location = LocationDto.empty,
            industry = null,
            salary = null,
            withoutSalaryButton = false
        )
    }
}
