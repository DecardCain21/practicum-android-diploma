package ru.practicum.android.diploma.domain.sharing

interface SharingInteract {
    fun shareUrl(url: String)
    fun sendEmail(email: String)
    fun dialPhoneNumber(phoneNumber: String)
}
