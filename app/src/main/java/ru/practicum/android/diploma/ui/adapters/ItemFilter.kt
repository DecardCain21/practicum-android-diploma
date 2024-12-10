package ru.practicum.android.diploma.ui.adapters

data class ItemFilter(
    val data: Any,
    val type: Int
) {
    companion object {
        const val TYPE_AREA = 0
        const val TYPE_REGION = 1
    }
}
