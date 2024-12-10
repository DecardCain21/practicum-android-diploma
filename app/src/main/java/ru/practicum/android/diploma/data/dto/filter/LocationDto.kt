package ru.practicum.android.diploma.data.dto.filter

import ru.practicum.android.diploma.data.dto.area.AreaDto
import ru.practicum.android.diploma.data.dto.area.SubAreaDto

data class LocationDto(
    val area: AreaDto?,
    val subArea: SubAreaDto?
) {

    companion object {
        val empty = LocationDto(area = null, subArea = null)
    }

}
