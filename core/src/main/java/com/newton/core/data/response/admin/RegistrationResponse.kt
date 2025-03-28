package com.newton.core.data.response.admin

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponse(
    val eventName: String,
    val eventDescription: String,
    val eventLocation: String,
    val course: String,
    val educationalLevel: String,
    val email: String,
    val event: Int,
    val expectations: String,
    val fullName: String,
    val phoneNumber: String,
    val registrationTimestamp: String,
    val ticketNumber: String,
    val uid: String,
    val eventDate: String,
    val isUsed: Boolean = false
)
