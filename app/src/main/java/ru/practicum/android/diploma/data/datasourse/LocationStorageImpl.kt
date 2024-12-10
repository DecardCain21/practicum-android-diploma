package ru.practicum.android.diploma.data.datasourse

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.data.dto.filter.LocationDto

class LocationStorageImpl(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences,
) : LocationStorage {

    override fun getLocation(): LocationDto {
        val json = sharedPreferences.getString(LOCATION_KEY, null)
        return if (json == null) {
            LocationDto.empty
        } else {
            gson.fromJson(json, LocationDto::class.java)
        }
    }

    override fun setLocation(location: LocationDto) {
        val json = gson.toJson(location)
        sharedPreferences.edit().putString(LOCATION_KEY, json).apply()
    }

    override fun clear() {
        sharedPreferences.edit().remove(LOCATION_KEY).apply()
    }

    companion object {
        private const val LOCATION_KEY = "LOCATION_KEY"
    }
}
