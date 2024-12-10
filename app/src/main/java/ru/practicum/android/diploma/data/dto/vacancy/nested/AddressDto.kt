package ru.practicum.android.diploma.data.dto.vacancy.nested

import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("building")
    val building: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("description")
    val description: Any,
    @SerializedName("id")
    val id: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("metro")
    val metro: MetroDto,
    @SerializedName("metro_stations")
    val metroStations: List<MetroStationDto>,
    @SerializedName("raw")
    val raw: String,
    @SerializedName("street")
    val street: String?
)
