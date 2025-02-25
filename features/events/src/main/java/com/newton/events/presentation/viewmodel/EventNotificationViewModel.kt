package com.newton.events.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.newton.events.domain.repository.EventNotificationService
import com.newton.events.presentation.view.ticket_screen.EventTicket
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventNotificationViewModel @Inject constructor(
    private val eventNotificationService: EventNotificationService
): ViewModel() {
    fun registerForEventNotifications(ticket: EventTicket) {
        eventNotificationService.scheduleEventReminder(ticket)
    }

    fun cancelEventNotifications(ticketId: String) {
        eventNotificationService.cancelEventReminder(ticketId)
    }

    fun updateEventNotifications(ticket: EventTicket) {
        eventNotificationService.rescheduleEventReminder(ticket)
    }
}