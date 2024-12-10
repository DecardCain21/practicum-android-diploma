package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.area.SubAreaDto
import ru.practicum.android.diploma.domain.models.Region

class RegionMapper {

    fun map(dto: SubAreaDto) = Region(id = dto.id, name = dto.name)

    fun map(region: Region, parentId: String) = SubAreaDto(
        id = region.id,
        name = region.name,
        parentId = parentId
    )

}
