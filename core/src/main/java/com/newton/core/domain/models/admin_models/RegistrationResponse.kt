package com.newton.core.domain.models.admin_models

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponse(
    val course: String,
    val educationalLevel: String,
    val email: String,
    val event: Int,
    val expectations: String,
    val fullName: String,
    val phoneNumber: String,
    val registrationTimestamp: String,
    val ticketNumber: String,
    val uid: String
)
