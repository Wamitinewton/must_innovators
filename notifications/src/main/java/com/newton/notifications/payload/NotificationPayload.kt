package com.newton.notifications.payload

data class NotificationPayload(
    val type: String,
    val title: String,
    val message: String,
    val eventId: String? = null,
    val timestamp: Long
)
