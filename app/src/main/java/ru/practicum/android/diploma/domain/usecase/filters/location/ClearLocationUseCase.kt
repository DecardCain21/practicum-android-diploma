package ru.practicum.android.diploma.domain.usecase.filters.location

import ru.practicum.android.diploma.domain.api.LocationRepository

class ClearLocationUseCase(
    private val repository: LocationRepository
) {

    fun execute() = repository.clear()

}
