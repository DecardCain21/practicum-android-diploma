package ru.practicum.android.diploma.domain.models

data class VacancySearchResult(
    val items: List<Vacancy>,
    val pages: Int,
    val totalVacancyCount: Int,
)
