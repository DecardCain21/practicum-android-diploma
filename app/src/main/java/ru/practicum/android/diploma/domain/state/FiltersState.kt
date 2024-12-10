package ru.practicum.android.diploma.domain.state

import ru.practicum.android.diploma.domain.models.Filter

data class FiltersState(
    val editor: Editor,
    val data: Data
) {

    sealed interface Editor {
        data object Changed : Editor
        data object Unchanged : Editor
    }

    sealed interface Data {
        data class Payload(val filters: Filter) : Data
    }
}
