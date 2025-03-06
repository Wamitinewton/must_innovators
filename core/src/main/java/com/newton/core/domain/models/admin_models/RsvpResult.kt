package com.newton.core.domain.models.admin_models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RsvpResult(
    @SerialName("course")
    val course: String = "",
    @SerialName("educational_level")
    val educationalLevel: String = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("event")
    val event: Int = 0,
    @SerialName("expectations")
    val expectations: String = "",
    @SerialName("full_name")
    val fullName: String = "",
    @SerialName("phone_number")
    val phoneNumber: String = "",
    @SerialName("registration_timestamp")
    val registrationTimestamp: String = "",
    @SerialName("ticket_number")
    val ticketNumber: String = "",
    @SerialName("uid")
    val uid: String = ""
)