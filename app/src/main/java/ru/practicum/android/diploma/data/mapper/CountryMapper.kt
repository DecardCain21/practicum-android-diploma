package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.area.AreaDto
import ru.practicum.android.diploma.data.dto.area.AreaResponse
import ru.practicum.android.diploma.domain.models.Country

class CountryMapper(
    private val regionMapper: RegionMapper
) {

    companion object {
        private const val OTHER_COUNTRY_ITEM_ID = "1001"
    }

    fun map(dto: AreaResponse): List<Country> {
        val countries = dto.area.map { map(it) }.toMutableList()

        countries.find { it.id == OTHER_COUNTRY_ITEM_ID }?.let {
            countries.remove(it)
            countries.add(it)
        }

        return countries
    }

    fun map(dto: AreaDto): Country {
        val regions = dto.areas.map { regionMapper.map(it) }
        return Country(id = dto.id, name = dto.name, regions = regions)
    }

    fun map(country: Country): AreaDto {
        val areas = country.regions.map { regionMapper.map(it, country.id) }
        return AreaDto(id = country.id, name = country.name, areas = areas)
    }
}
