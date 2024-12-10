package ru.practicum.android.diploma.data.dto.vacancy.nested

import com.google.gson.annotations.SerializedName

data class SalaryDto(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("from")
    val from: Int?,
    @SerializedName("gross")
    val gross: Boolean,
    @SerializedName("to")
    val to: Int?
)
