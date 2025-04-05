package com.newton.notifications.services

import com.google.firebase.messaging.*
import com.newton.notifications.data.repository.*
import com.newton.notifications.manager.*
import com.newton.notifications.payload.*
import dagger.hilt.android.*
import kotlinx.coroutines.*
import javax.inject.*

@AndroidEntryPoint
class FCMService : FirebaseMessagingService() {
    @Inject
    lateinit var notificationsManager: NotificationsManager

    @Inject
    lateinit var fcmTokenRepository: FCMTokenRepository

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.data.isNotEmpty()) {
            val notificationPayload =
                NotificationPayload(
                    type = message.data["type"] ?: "general",
                    title = message.data["title"] ?: "New Notification",
                    message = message.data["message"] ?: "",
                    eventId = message.data["eventId"],
                    timestamp = message.data["timestamp"]?.toLongOrNull()
                        ?: System.currentTimeMillis()
                )

            processNotification(notificationPayload)
        }

        message.notification?.let { notification ->

            val notificationPayload =
                NotificationPayload(
                    type = "general",
                    title = notification.title ?: "New Notification",
                    message = notification.body ?: "",
                    eventId = null,
                    timestamp = System.currentTimeMillis()
                )

            processNotification(notificationPayload)
        }
    }

    override fun onNewToken(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            fcmTokenRepository.saveToken(token)
            fcmTokenRepository.sendTokenToBackend(token)
        }
    }

    private fun processNotification(payload: NotificationPayload) {
        when (payload.type) {
            "event_reminder" -> {
                payload.eventId?.let {
                    notificationsManager.handleEventReminder(payload)
                }
            }

            "event_starting" -> {
                payload.eventId?.let {
                    notificationsManager.handleEventStarting(payload)
                }
            }

            "event_completed" -> {
                payload.eventId?.let {
                    notificationsManager.handleEventCompleted(payload)
                }
            }

            else -> {
                notificationsManager.handleGeneralNotification(payload)
            }
        }
    }
}
