package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.filter.LocationDto
import ru.practicum.android.diploma.domain.models.Location

class LocationMapper(
    private val countryMapper: CountryMapper,
    private val regionMapper: RegionMapper
) {

    fun map(dto: LocationDto) = Location(
        country = dto.area?.let { countryMapper.map(it) },
        region = dto.subArea?.let { regionMapper.map(it) }
    )

    fun map(location: Location): LocationDto {
        val area = location.country?.let { countryMapper.map(it) }
        val subArea = if (area != null) {
            location.region?.let { regionMapper.map(it, area.id) }
        } else {
            null
        }

        return LocationDto(area, subArea)
    }
}
