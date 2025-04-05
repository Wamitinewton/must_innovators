package com.newton.core.domain.models.eventModels

import kotlinx.serialization.*

@Serializable
data class EventRegistrationRequest(
    val full_name: String,
    val email: String,
    val course: String,
    val educational_level: String,
    val phone_number: String,
    val expectations: String
)
