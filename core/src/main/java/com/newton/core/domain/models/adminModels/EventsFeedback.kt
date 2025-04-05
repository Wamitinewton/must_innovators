package com.newton.core.domain.models.adminModels

data class EventsFeedback(
    val id: String,
    val attendeeId: String,
    val rating: Int,
    val comment: String,
    val submittedAt: String,
    val attendeeName: String
)
