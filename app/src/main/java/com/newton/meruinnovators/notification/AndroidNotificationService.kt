package com.newton.meruinnovators.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.newton.core.notification_service.NotificationService
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidNotificationService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val workManager: WorkManager
): NotificationService {
    init {
        createNotificationChannel()
    }

    override fun scheduleNotification(
        notificationId: String,
        title: String,
        message: String,
        scheduledTime: ZonedDateTime,
        data: Map<String, String>
    ) {
        val now = ZonedDateTime.now()
        val delay = Duration.between(now, scheduledTime)

        if (delay.isNegative) {
            return // Don't schedule past notifications
        }

        val inputData = Data.Builder()
            .putString(NotificationWorker.KEY_NOTIFICATION_ID, notificationId)
            .putString(NotificationWorker.KEY_TITLE, title)
            .putString(NotificationWorker.KEY_MESSAGE, message)
            .apply {
                data.forEach { (key, value) ->
                    putString("data_$key", value)
                }
            }
            .build()

        val notificationWork = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay.toMillis(), TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .addTag(notificationId)
            .build()

        workManager.enqueueUniqueWork(
            notificationId,
            ExistingWorkPolicy.KEEP,
            notificationWork
        )
    }

    override fun cancelNotification(notificationId: String) {
        workManager.cancelUniqueWork(notificationId)

        // Also cancel if already shown
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.cancel(notificationId.hashCode())
    }

    override fun cancelAllNotifications() {
        workManager.cancelAllWork()

        // Also cancel already shown notifications
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.cancelAll()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = CHANNEL_ID
            val name = "Event Reminders"
            val description = "Notifications for upcoming events"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelId, name, importance).apply {
                this.description = description
                enableVibration(true)
                enableLights(true)
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "event_reminders_channel"
    }
}