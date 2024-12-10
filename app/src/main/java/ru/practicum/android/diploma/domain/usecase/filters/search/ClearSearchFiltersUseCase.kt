package ru.practicum.android.diploma.domain.usecase.filters.search

import ru.practicum.android.diploma.domain.api.FilterRepository

class ClearSearchFiltersUseCase(
    private val repository: FilterRepository
) {

    fun execute() = repository.clearSearchFilters()

}
