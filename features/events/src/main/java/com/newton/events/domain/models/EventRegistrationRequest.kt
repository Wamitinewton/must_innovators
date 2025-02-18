package com.newton.events.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class EventRegistrationRequest(
    val full_name: String,
    val email: String,
    val course: String,
    val educational_level: String,
    val phone_number: String,
    val expectations: String
)
