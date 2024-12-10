package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val imageUrl: String?,
    val name: String,
    val city: String,
    val salaryFrom: Int,
    val salaryTo: Int,
    val currency: String,
    val employerName: String,
    val experience: String,
    val employmentName: String,
    val schedule: String
)
