package ru.practicum.android.diploma.data.dto.vacancy.nested

import com.google.gson.annotations.SerializedName

data class LogoUrlsDto(
    @SerializedName("original")
    val original: String,
    @SerializedName("240")
    val medium: String,
    @SerializedName("90")
    val small: String
)
