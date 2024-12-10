package ru.practicum.android.diploma.domain.models

data class Location(
    val country: Country?,
    val region: Region?
) {

    val isEmpty: Boolean
        get() = country?.name.isNullOrEmpty() && region?.name.isNullOrEmpty()

    val description: String?
        get() = if (country == null) {
            region?.name
        } else {
            region?.let { country.name + ", " + it.name } ?: country.name
        }

    companion object {
        val empty = Location(country = null, region = null)
    }
}
