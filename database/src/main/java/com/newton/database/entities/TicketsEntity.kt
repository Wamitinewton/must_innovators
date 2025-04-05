package com.newton.database.entities

import androidx.room.*

@Entity(tableName = "eventTickets")
data class TicketsEntity(
    @PrimaryKey
    val ticketNumber: String,
    val course: String,
    val educationalLevel: String,
    val email: String,
    val event: Int,
    val expectations: String,
    val fullName: String,
    val phoneNumber: String,
    val registrationTimestamp: String,
    val uid: String,
    val eventName: String,
    val eventDescription: String,
    val eventLocation: String,
    val eventDate: String,
    val isUsed: Boolean
)
