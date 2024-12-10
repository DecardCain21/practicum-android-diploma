package ru.practicum.android.diploma.data.dto.area

data class AreaDto(
    val id: String,
    val name: String,
    val areas: List<SubAreaDto>,
)
