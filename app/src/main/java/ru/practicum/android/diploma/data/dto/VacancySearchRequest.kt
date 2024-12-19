package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.network.Request

data class VacancySearchRequest(
    val options: Map<String, Any>
) : Request
