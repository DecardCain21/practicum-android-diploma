package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.datasourse.LocationStorage
import ru.practicum.android.diploma.data.mapper.LocationMapper
import ru.practicum.android.diploma.domain.api.LocationRepository
import ru.practicum.android.diploma.domain.models.Location

class LocationRepositoryImpl(
    private val mapper: LocationMapper,
    private val storage: LocationStorage
) : LocationRepository {

    override fun getLocation(): Location =
        mapper.map(storage.getLocation())

    override fun setLocation(location: Location) =
        storage.setLocation(mapper.map(location))

    override fun clear() = storage.clear()

}
