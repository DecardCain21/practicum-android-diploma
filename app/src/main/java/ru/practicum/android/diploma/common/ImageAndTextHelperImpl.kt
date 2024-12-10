package ru.practicum.android.diploma.common

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import ru.practicum.android.diploma.util.ImageAndTextHelper

class ImageAndTextHelperImpl : ImageAndTextHelper {
    override fun setImageAndText(
        context: Context,
        imageView: ImageView,
        textView: TextView,
        imageResId: Int,
        text: String
    ) {
        imageView.setImageResource(imageResId)
        textView.text = text
    }
}
