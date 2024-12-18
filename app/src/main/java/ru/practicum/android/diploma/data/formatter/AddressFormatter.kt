package ru.practicum.android.diploma.data.formatter

import ru.practicum.android.diploma.common.AppConstants.EMPTY_PARAM_VALUE
import ru.practicum.android.diploma.data.dto.vacancy.nested.AddressDto

object AddressFormatter {

    fun format(address: AddressDto?): String {
        return address?.let {
            when {
                it.city.isNullOrEmpty() -> EMPTY_PARAM_VALUE
                it.street.isNullOrEmpty() -> "${it.city}, ${it.building}"
                it.building.isNullOrEmpty() -> "${it.city}, ${it.street}"
                else -> "${it.city}, ${it.street}, ${it.building}"
            }
        } ?: EMPTY_PARAM_VALUE
    }
}
