package ru.practicum.android.diploma.util

import android.content.Context
import android.widget.ImageView
import android.widget.TextView

interface ImageAndTextHelper {

    fun setImageAndText(
        context: Context,
        imageView: ImageView,
        textView: TextView,
        imageResId: Int,
        text: String
    )
}
