package ru.practicum.android.diploma.domain.models

data class Filter(
    val location: Location?,
    val industry: Industry?,
    val salary: Int?,
    val withoutSalaryButton: Boolean
) {

    companion object {
        val empty = Filter(
            location = Location.empty,
            industry = null,
            salary = null,
            withoutSalaryButton = false
        )
    }
}
