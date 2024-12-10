package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.vacancy.self.VacancyDto

data class VacanciesSearchResponse(
    @SerializedName("alternate_url")
    val alternateUrl: String,
    @SerializedName("arguments")
    val arguments: Any,
    @SerializedName("clusters")
    val clusters: Any,
    @SerializedName("fixes")
    val fixes: Any,
    @SerializedName("found")
    val found: Int,
    @SerializedName("items")
    val items: List<VacancyDto>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("suggests")
    val suggests: Any
) : Response()
