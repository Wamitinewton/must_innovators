package com.newton.core.domain.models.admin_models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class EventFeedback(
    val id: String,
    val attendeeId: String,
    val rating: Int, // 1-5
    val comment: String,
    @Contextual
    val submittedAt: LocalDateTime
)
