package com.newton.core.data.response.events_response

import kotlinx.serialization.Serializable

@Serializable
data class EventRegistrationResponse(
    val data: EventRegistrationResponseDto,
    val message: String,
    val status: String
)

@Serializable
data class EventRegistrationResponseDto(
    val course: String,
    val educational_level: String,
    val email: String,
    val event: Int,
    val expectations: String,
    val full_name: String,
    val phone_number: String,
    val registration_timestamp: String,
    val ticket_number: String,
    val uid: String
)