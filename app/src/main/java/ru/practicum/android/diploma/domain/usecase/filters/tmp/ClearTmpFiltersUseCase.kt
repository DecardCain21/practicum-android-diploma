package ru.practicum.android.diploma.domain.usecase.filters.tmp

import ru.practicum.android.diploma.domain.api.FilterRepository

class ClearTmpFiltersUseCase(
    private val repository: FilterRepository
) {

    fun execute() = repository.clearTmpFilters()

}
