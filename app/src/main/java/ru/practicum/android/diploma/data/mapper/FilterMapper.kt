package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.area.AreaDto
import ru.practicum.android.diploma.data.dto.area.SubAreaDto
import ru.practicum.android.diploma.data.dto.filter.FilterDto
import ru.practicum.android.diploma.data.dto.industry.IndustryDto
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Region

class FilterMapper {

    fun map(dto: FilterDto) = Filter(
        country = dto.area?.let { map(it) },
        region = dto.subArea?.let { map(it) },
        industry = dto.industry?.let { map(it) },
        salary = dto.salary,
        withoutSalaryButton = dto.withoutSalaryButton
    )

    fun map(filter: Filter) = FilterDto(
        area = filter.country?.let { map(it) },
        subArea = filter.region?.let { map(it, it.id) },
        industry = filter.industry?.let { map(it) },
        salary = filter.salary,
        withoutSalaryButton = filter.withoutSalaryButton
    )

    private fun map(dto: AreaDto): Country {
        val regions = dto.areas.map { map(it) }
        return Country(id = dto.id, name = dto.name, regions = regions)
    }

    private fun map(country: Country): AreaDto {
        val areas = country.regions.map { map(it, country.id) }
        return AreaDto(id = country.id, name = country.name, areas = areas)
    }

    private fun map(dto: SubAreaDto) = Region(id = dto.id, name = dto.name)

    private fun map(region: Region, parentId: String) = SubAreaDto(
        id = region.id,
        name = region.name,
        parentId = parentId
    )

    private fun map(dto: IndustryDto) = Industry(id = dto.id, name = dto.name)

    private fun map(industry: Industry) = IndustryDto(id = industry.id, name = industry.name)

}
