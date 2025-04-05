package com.newton.notifications.services

import android.app.*
import android.content.*
import android.media.*
import androidx.core.app.*
import com.newton.commonUi.*
import javax.inject.*

class NotificationService
@Inject
constructor(
    private val context: Context
) {
    companion object {
        const val CHANNEL_EVENTS = "events_channel"
        const val CHANNEL_GENERAL = "general_channel"

        private const val EVENT_NOTIFICATION_ID = 1000
    }

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        val eventsChannel =
            NotificationChannel(
                CHANNEL_EVENTS,
                "Event Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "event notifications"
                enableVibration(true)
                enableLights(true)
            }

        val generalChannel =
            NotificationChannel(
                CHANNEL_GENERAL,
                "General Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "event notifications"
                enableVibration(true)
                enableLights(true)
            }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannels(listOf(eventsChannel, generalChannel))
    }

    /**
     * Display an event reminder notification
     */

    fun shownEventNotification(
        title: String,
        message: String,
        eventId: String? = null,
        notificationId: Int = EVENT_NOTIFICATION_ID
    ) {
        val intent =
            Intent().apply {
                setClassName(context.packageName, "${context.packageName}.MainActivity")
                putExtra("eventId", eventId)
                putExtra("fromNotification", true)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder =
            NotificationCompat.Builder(context, CHANNEL_EVENTS)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(context)) {
            try {
                notify(notificationId, notificationBuilder.build())
            } catch (e: SecurityException) {
            }
        }
    }

    fun cancelNotification(notificationId: Int) {
        NotificationManagerCompat.from(context).cancel(notificationId)
    }

    fun cancelAllNotifications() {
        NotificationManagerCompat.from(context).cancelAll()
    }
}
