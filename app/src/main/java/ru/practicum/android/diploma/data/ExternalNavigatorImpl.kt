package ru.practicum.android.diploma.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.domain.sharing.ExternalNavigator

class ExternalNavigatorImpl(
    private val context: Context
) : ExternalNavigator {

    override fun shareUrl(url: String, title: String) {
        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, url)

        val chooserIntent = Intent.createChooser(intent, title)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        context.startActivity(chooserIntent)
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
