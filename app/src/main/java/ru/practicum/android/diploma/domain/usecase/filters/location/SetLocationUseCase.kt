package ru.practicum.android.diploma.domain.usecase.filters.location

import ru.practicum.android.diploma.domain.api.LocationRepository
import ru.practicum.android.diploma.domain.models.Location

class SetLocationUseCase(
    private val repository: LocationRepository
) {

    fun execute(location: Location) = repository.setLocation(location)

}
