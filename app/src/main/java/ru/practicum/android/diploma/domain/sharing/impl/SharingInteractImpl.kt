package ru.practicum.android.diploma.domain.sharing.impl

import ru.practicum.android.diploma.domain.sharing.ExternalNavigator
import ru.practicum.android.diploma.domain.sharing.SharingInteract

class SharingInteractImpl(private val externalNavigator: ExternalNavigator) : SharingInteract {

    override fun shareUrl(url: String) {
        externalNavigator.shareUrl(url)
    }

    override fun sendEmail(email: String) {
        externalNavigator.sendEmail(email)
    }

    override fun dialPhoneNumber(phoneNumber: String) {
        externalNavigator.dialPhoneNumber(phoneNumber)
    }
}

