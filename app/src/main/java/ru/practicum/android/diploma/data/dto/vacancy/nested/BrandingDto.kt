package ru.practicum.android.diploma.data.dto.vacancy.nested

import com.google.gson.annotations.SerializedName

data class BrandingDto(
    @SerializedName("tariff")
    val tariff: Any,
    @SerializedName("type")
    val type: String
)
