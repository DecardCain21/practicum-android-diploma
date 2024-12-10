package ru.practicum.android.diploma.domain.usecase.filters.tmp

import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.models.Filter

class SetTmpFiltersUseCase(
    private val repository: FilterRepository
) {

    fun execute(filters: Filter) = repository.setTmpFilters(filters)

}
