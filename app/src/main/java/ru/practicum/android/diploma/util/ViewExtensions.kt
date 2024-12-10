package ru.practicum.android.diploma.util

import android.text.Editable
import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

fun Editable?.toIntOrNull(): Int? {
    return toString().toIntOrNull()
}
