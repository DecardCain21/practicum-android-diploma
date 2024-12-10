package ru.practicum.android.diploma.domain.usecase.filters.tmp

import ru.practicum.android.diploma.domain.api.FilterRepository

class GetTmpFiltersUseCase(
    private val filterRepository: FilterRepository
) {

    fun execute() = filterRepository.getTmpFilters()
}
