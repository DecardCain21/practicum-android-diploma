package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Filter

interface FilterRepository {

    fun getTmpFilters(): Filter
    fun setTmpFilters(value: Filter)
    fun clearTmpFilters()

    fun getSearchFilters(): Filter
    fun setSearchFilters(value: Filter)
    fun clearSearchFilters()

}
