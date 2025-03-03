package com.newton.events.presentation.view.ticket_screen

import java.time.LocalDateTime

data class EventTicket(
    val id: String,
    val ticketNumber: String,
    val eventName: String,
    val eventDescription: String,
    val eventDate: String,
    val eventLocation: String,
    val registrationDate: String,
    val ticketType: TicketType = TicketType.STANDARD,
    val isUsed: Boolean = false
)

enum class TicketType {
    STANDARD, EARLY_BIRD
}
