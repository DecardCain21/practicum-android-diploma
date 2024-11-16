package ru.practicum.android.diploma.data.dto.vacancy.nested

import com.google.gson.annotations.SerializedName

data class WorkingItemDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)
