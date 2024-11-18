package ru.practicum.android.diploma.domain.models

data class VacancyDetails(
    val id: String,
    val name: String,
    val city: String,
    val imageUrl: String?,
    val salaryFrom: Int,
    val salaryTo: Int,
    val currency: String,
    val employerName: String,
    val experience: String,
    val employmentName: String,
    val schedule: String,
    val description: String,
    val descriptionSkills: String,
    val url: String
)
