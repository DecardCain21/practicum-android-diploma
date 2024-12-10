package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Location

interface LocationRepository {

    fun getLocation(): Location

    fun setLocation(location: Location)

    fun clear()

}
