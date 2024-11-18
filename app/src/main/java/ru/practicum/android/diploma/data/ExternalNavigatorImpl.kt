package ru.practicum.android.diploma.data

import android.content.Context
import android.content.Intent
import android.net.Uri

import ru.practicum.android.diploma.domain.sharing.ExternalNavigator

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {

    override fun shareUrl(url: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        shareIntent.setType("text/playn")
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        context.startActivity(shareIntent)
    }

    override fun sendEmail(email: String) {
        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
        }
    }

    override fun dialPhoneNumber(phoneNumber: String) {
        Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
    }
}
