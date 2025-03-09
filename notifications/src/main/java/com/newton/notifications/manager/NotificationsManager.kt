package com.newton.notifications.manager

import android.content.Context
import com.google.firebase.messaging.FirebaseMessaging
import com.newton.notifications.data.repository.FCMTokenRepository
import com.newton.notifications.payload.NotificationPayload
import com.newton.notifications.permissions.NotificationPermissionChecker
import com.newton.notifications.services.NotificationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsManager @Inject constructor(
    private val context: Context,
    private val notificationService: NotificationService,
    private val fcmTokenRepository: FCMTokenRepository
) {


    private val _latestNotification = MutableStateFlow<NotificationPayload?>(null)
    val latestNotifications: StateFlow<NotificationPayload?> = _latestNotification

    private val _notificationPermissionGranted = MutableStateFlow(false)
    val notificationPermissionGranted: StateFlow<Boolean> = _notificationPermissionGranted

    /**
     * Initialize FCM and register for token
     */
    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token = FirebaseMessaging.getInstance().token.await()

                fcmTokenRepository.saveToken(token)

                fcmTokenRepository.sendTokenToBackend(token)
                    .onFailure { e ->
                        Timber.e(e, e.message)
                    }
            } catch (e: Exception) {
                Timber.e(e, "Error initializing FCM")
            }
        }
    }

    /**
     * Check if notification permission has been granted
     */
    fun checkNotificationPermission() {
        // On Android 13+ (API 33+), check for notification permission
        // For older versions, we assume permission is granted with the app installation
        val permissionGranted = NotificationPermissionChecker.hasNotificationPermission(context)
        _notificationPermissionGranted.value = permissionGranted
    }

    /**
     * Handle event reminder notification
     */

    fun handleEventReminder(payload: NotificationPayload) {
        _latestNotification.value = payload
        notificationService.shownEventNotification(
            title = payload.title,
            message = payload.message,
            eventId = payload.eventId ?: "",
            notificationId = (payload.eventId?.hashCode() ?: 0) + 1000
        )

        CoroutineScope(Dispatchers.Main).launch {
            _latestNotification.emit(payload)
        }
    }

    /**
     * Handle event starting soon notification
     */

    fun handleEventStarting(payload: NotificationPayload) {
        _latestNotification.value = payload
        notificationService.shownEventNotification(
            title = payload.title,
            message = payload.message,
            eventId = payload.eventId ?: "",
            notificationId = (payload.eventId?.hashCode() ?: 0) + 2000
        )
        CoroutineScope(Dispatchers.Main).launch {
            _latestNotification.emit(payload)
        }
    }

    /**
     * Handle event completed notification
     */
    fun handleEventCompleted(payload: NotificationPayload) {
        _latestNotification.value = payload
        notificationService.shownEventNotification(
            title = payload.title,
            message = payload.message,
            eventId = payload.eventId ?: "",
            notificationId = (payload.eventId?.hashCode() ?: 0) + 3000
        )

        CoroutineScope(Dispatchers.Main).launch {
            _latestNotification.emit(payload)
        }
    }
    /**
     * Handle general notification
     */
    fun handleGeneralNotification(payload: NotificationPayload) {
        _latestNotification.value = payload
        notificationService.shownEventNotification(
            title = payload.title,
            message = payload.message,
            eventId = payload.eventId,
            notificationId = (payload.title.hashCode() + payload.message.hashCode())
        )

        CoroutineScope(Dispatchers.Main).launch {
            _latestNotification.emit(payload)
        }
    }
}