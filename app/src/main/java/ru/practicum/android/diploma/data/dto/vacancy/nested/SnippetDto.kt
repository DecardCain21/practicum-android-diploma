package ru.practicum.android.diploma.data.dto.vacancy.nested

import com.google.gson.annotations.SerializedName

data class SnippetDto(
    @SerializedName("requirement")
    val requirement: String,
    @SerializedName("responsibility")
    val responsibility: String
)
