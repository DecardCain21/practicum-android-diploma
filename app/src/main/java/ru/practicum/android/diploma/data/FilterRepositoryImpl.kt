package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.datasourse.FilterStorage
import ru.practicum.android.diploma.data.mapper.FilterMapper
import ru.practicum.android.diploma.domain.api.FilterRepository
import ru.practicum.android.diploma.domain.models.Filter

class FilterRepositoryImpl(
    private val mapper: FilterMapper,
    private val filterStorage: FilterStorage,
) : FilterRepository {

    override fun setTmpFilters(value: Filter) {
        filterStorage.tmpFilters = mapper.map(value)
    }

    override fun clearTmpFilters() = filterStorage.clearTmpFilers()

    override fun getSearchFilters(): Filter = mapper.map(filterStorage.searchFilters)

    override fun setSearchFilters(value: Filter) {
        filterStorage.searchFilters = mapper.map(value)
    }

    override fun clearSearchFilters() = filterStorage.clearSearchFilters()

    override fun getTmpFilters(): Filter = mapper.map(filterStorage.tmpFilters)
}
