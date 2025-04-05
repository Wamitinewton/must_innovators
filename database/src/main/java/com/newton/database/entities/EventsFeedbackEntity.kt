package com.newton.database.entities

import androidx.room.*

@Entity(tableName = "events_feedback")
data class EventsFeedbackEntity(
    @PrimaryKey
    val id: String,
    val attendeeId: String,
    val rating: Int,
    val comment: String,
    val submittedAt: String,
    val attendeeName: String
)
