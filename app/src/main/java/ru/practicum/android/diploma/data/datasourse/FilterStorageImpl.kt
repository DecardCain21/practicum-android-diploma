package ru.practicum.android.diploma.data.datasourse

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.dto.filter.FilterDto

class FilterStorageImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : FilterStorage {

    override var tmpFilters: FilterDto
        get() = getFilters(FILTERS_KEY)
        set(value) = setFilters(value, FILTERS_KEY)

    override fun clearTmpFilers() = clear(FILTERS_KEY)

    override var searchFilters: FilterDto
        get() = getFilters(SEARCH_FILTERS_KEY)
        set(value) = setFilters(value, SEARCH_FILTERS_KEY)

    override fun clearSearchFilters() = clear(SEARCH_FILTERS_KEY)

    private fun getFilters(key: String): FilterDto {
        val json = sharedPreferences.getString(key, null)
        return if (json == null) {
            FilterDto.empty
        } else {
            gson.fromJson(json, object : TypeToken<FilterDto>() {}.type)
        }
    }

    private fun setFilters(filters: FilterDto, key: String) {
        val json = gson.toJson(filters)
        sharedPreferences.edit().putString(key, json).apply()
    }

    private fun clear(key: String) = sharedPreferences.edit().remove(key).apply()

    companion object {
        private const val FILTERS_KEY = "FILTERS_KEY"
        private const val SEARCH_FILTERS_KEY = "SEARCH_FILTERS_KEY"
    }
}
