package com.newton.events.domain.repository

import com.newton.events.presentation.view.ticket_screen.EventTicket

interface EventNotificationService {
    fun scheduleEventReminder(ticket: EventTicket)
    fun cancelEventReminder(ticketId: String)
    fun rescheduleEventReminder(ticket: EventTicket)
}