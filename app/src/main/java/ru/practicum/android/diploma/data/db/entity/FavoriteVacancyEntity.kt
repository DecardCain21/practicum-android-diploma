package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies")
data class FavoriteVacancyEntity(
    @PrimaryKey
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
    val timestamp: Long,
    val url: String
)
