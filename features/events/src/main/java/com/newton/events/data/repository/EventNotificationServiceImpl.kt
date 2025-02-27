package com.newton.events.data.repository

import com.newton.core.notification_service.NotificationService
import com.newton.events.domain.repository.EventNotificationService
import com.newton.events.presentation.view.ticket_screen.EventTicket
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class EventNotificationServiceImpl @Inject constructor(
    private val notificationService: NotificationService
): EventNotificationService {
    override fun scheduleEventReminder(ticket: EventTicket) {
        val eventDateTime = ZonedDateTime.parse(ticket.eventDate)

        // Schedule multiple reminders at different intervals
        scheduleReminderBeforeEvent(ticket, eventDateTime, 7, ChronoUnit.DAYS, "Upcoming in 1 week")
        scheduleReminderBeforeEvent(ticket, eventDateTime, 1, ChronoUnit.DAYS, "Tomorrow")
        scheduleReminderBeforeEvent(ticket, eventDateTime, 3, ChronoUnit.HOURS, "In few hours")
        scheduleReminderBeforeEvent(ticket, eventDateTime, 30, ChronoUnit.MINUTES, "Starting soon")
    }

    override fun cancelEventReminder(ticketId: String) {
        // Cancel all reminders associated with this ticket
        listOf(
            "${ticketId}_7days",
            "${ticketId}_1day",
            "${ticketId}_3hours",
            "${ticketId}_30mins"
        ).forEach { notificationId ->
            notificationService.cancelNotification(notificationId)
        }
    }

    override fun rescheduleEventReminder(ticket: EventTicket) {
        // First cancel existing reminders
        cancelEventReminder(ticket.id)
        // Then schedule new ones
        scheduleEventReminder(ticket)
    }

    private fun scheduleReminderBeforeEvent(
        ticket: EventTicket,
        eventDateTime: ZonedDateTime,
        amount: Long,
        unit: ChronoUnit,
        timeDescription: String
    ) {
        val reminderTime = eventDateTime.minus(amount, unit)
        val now = ZonedDateTime.now()

        // Only schedule if the reminder time is in the future
        if (reminderTime.isAfter(now)) {
            val notificationId = "${ticket.id}_${unit.name.lowercase()}_${amount}"
            val title = "Reminder: ${ticket.eventName}"
            val message = "$timeDescription: ${ticket.eventName} at ${ticket.eventLocation}"

            val data = mapOf(
                "ticketId" to ticket.id,
                "eventName" to ticket.eventName,
                "eventLocation" to ticket.eventLocation,
                "ticketNumber" to ticket.ticketNumber
            )

            notificationService.scheduleNotification(
                notificationId = notificationId,
                title = title,
                message = message,
                scheduledTime = reminderTime,
                data = data
            )
        }
    }
}