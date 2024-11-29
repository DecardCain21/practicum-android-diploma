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
        area = dto.area?.let { convertToCountry(it) },
        region = dto.subArea?.let { convertToRegion(it) },
        industry = dto.industry?.let { convertToIndustry(it) },
        salary = dto.salary,
        withoutSalaryButton = dto.withoutSalaryButton
    )

    fun map(filter: Filter) = FilterDto(
        area = filter.area?.let { convertToArea(it) },
        subArea = filter.region?.let { convertToSubAreaDto(it, it.id) },
        industry = filter.industry?.let { convertToIndustryDto(it) },
        salary = filter.salary,
        withoutSalaryButton = filter.withoutSalaryButton
    )

    private fun convertToCountry(dto: AreaDto): Country {
        val regions = dto.areas.map { convertToRegion(it) }
        return Country(id = dto.id, name = dto.name, regions = regions)
    }

    private fun convertToArea(country: Country): AreaDto {
        val areas = country.regions.map { convertToSubAreaDto(it, country.id) }
        return AreaDto(id = country.id, name = country.name, areas = areas)
    }

    private fun convertToRegion(dto: SubAreaDto) = Region(id = dto.id, name = dto.name)

    private fun convertToSubAreaDto(region: Region, parentId: String) = SubAreaDto(
        id = region.id,
        name = region.name,
        parentId = parentId
    )

    private fun convertToIndustry(dto: IndustryDto) = Industry(id = dto.id, name = dto.name)

    private fun convertToIndustryDto(industry: Industry) = IndustryDto(id = industry.id, name = industry.name)

}
