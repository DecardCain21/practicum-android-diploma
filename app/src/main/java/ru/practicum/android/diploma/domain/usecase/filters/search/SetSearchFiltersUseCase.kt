package ru.practicum.android.diploma.domain.usecase.filters.search

import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.models.Filter

class SetSearchFiltersUseCase(
    private val filterRepository: FilterRepository
) {
    fun execute(newItems: Filter) = filterRepository.setSearchFilters(newItems)
}
