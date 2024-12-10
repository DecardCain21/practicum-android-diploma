package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.Resource

interface CountryRepository {
    suspend fun getCountry(): Resource<List<Country>>
}
