package ru.practicum.android.diploma.data.dto

data class VacancySearchRequest(
    val expression: String,
    val page: Int,
    val perPage: Int = 20
)
