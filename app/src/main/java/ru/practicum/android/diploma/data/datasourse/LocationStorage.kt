package ru.practicum.android.diploma.data.datasourse

import ru.practicum.android.diploma.data.dto.filter.LocationDto

interface LocationStorage {

    fun getLocation(): LocationDto

    fun setLocation(location: LocationDto)

    fun clear()

}
