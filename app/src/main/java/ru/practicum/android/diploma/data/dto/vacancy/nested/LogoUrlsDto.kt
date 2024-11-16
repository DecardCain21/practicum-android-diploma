package ru.practicum.android.diploma.data.dto.vacancy.nested

import com.google.gson.annotations.SerializedName

data class LogoUrlsDto(
    @SerializedName("original")
    val original: String,
    @SerializedName("240")
    val x240: String,
    @SerializedName("90")
    val x90: String
)
