package ru.practicum.android.diploma.domain.models

data class Filter(
    val country: Country?,
    val region: Region?,
    val industry: Industry?,
    val salary: Int?,
    val withoutSalaryButton: Boolean
) {

    val location: String?
        get() {
            return if (country == null) {
                region?.name
            } else {
                region?.let { country.name + ", " + it.name } ?: country.name
            }
        }

    companion object {
        val empty = Filter(
            country = null,
            region = null,
            industry = null,
            salary = null,
            withoutSalaryButton = false
        )
    }
}
