package ru.practicum.android.diploma.domain.usecase.filters

import ru.practicum.android.diploma.domain.api.FilterRepository

class GetFiltersUseCase(
    private val filterRepository: FilterRepository
) {

    fun execute() = filterRepository.getFilters()
}
