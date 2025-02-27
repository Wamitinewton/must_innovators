package com.newton.core.domain.models.event_models

import kotlinx.serialization.Serializable

@Serializable
data class Attendee(
    val id: String,
    val name: String,
    val email: String,
    val profilePicUrl: String?,
    val isAttending: Boolean,
    val hasCheckedIn: Boolean = false
)
