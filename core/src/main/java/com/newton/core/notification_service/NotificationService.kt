package com.newton.core.notification_service

import java.time.ZonedDateTime

interface NotificationService {
    fun scheduleNotification(
        notificationId: String,
        title: String,
        message: String,
        scheduledTime: ZonedDateTime,
        data: Map<String, String> = emptyMap()
    )

    fun cancelNotification(notificationId: String)

    fun cancelAllNotifications()
}