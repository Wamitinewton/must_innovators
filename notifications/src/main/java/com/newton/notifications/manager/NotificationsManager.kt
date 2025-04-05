package com.newton.notifications.manager

import android.content.*
import com.google.firebase.messaging.*
import com.newton.notifications.data.repository.*
import com.newton.notifications.payload.*
import com.newton.notifications.permissions.*
import com.newton.notifications.services.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.*
import timber.log.*
import javax.inject.*

@Singleton
class NotificationsManager
@Inject
constructor(
    private val context: Context,
    private val notificationService: NotificationService,
    private val fcmTokenRepository: FCMTokenRepository
) {
    private val _latestNotification = MutableStateFlow<NotificationPayload?>(null)
    val latestNotifications: StateFlow<NotificationPayload?> = _latestNotification

    private val _notificationPermissionGranted = MutableStateFlow(false)
    val notificationPermissionGranted: StateFlow<Boolean> = _notificationPermissionGranted

    init {
        checkNotificationPermission()
    }

    /**
     * Initialize FCM and register for token
     */
    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token = FirebaseMessaging.getInstance().token.await()

                Timber.d("FCM initialized: $token")

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

    fun disableNotifications() {
        notificationService.cancelAllNotifications()
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
