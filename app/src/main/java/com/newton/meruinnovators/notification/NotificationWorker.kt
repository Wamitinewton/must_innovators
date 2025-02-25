package com.newton.meruinnovators.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.newton.common_ui.R
import com.newton.meruinnovators.activity.MainActivity

class NotificationWorker(
    private val context: Context,
    params: WorkerParameters
): Worker(context, params) {
        override fun doWork(): Result {
            val notificationId = inputData.getString(KEY_NOTIFICATION_ID) ?: return Result.failure()
            val title = inputData.getString(KEY_TITLE) ?: return Result.failure()
            val message = inputData.getString(KEY_MESSAGE) ?: return Result.failure()

            // Extract additional data
            val dataMap = mutableMapOf<String, String>()
            inputData.keyValueMap.forEach { (key, value) ->
                if (key.startsWith("data_") && value is String) {
                    val dataKey = key.removePrefix("data_")
                    dataMap[dataKey] = value
                }
            }

            showNotification(notificationId, title, message, dataMap)
            return Result.success()
        }

        private fun showNotification(
            notificationId: String,
            title: String,
            message: String,
            data: Map<String, String>
        ) {
            // Create intent for when notification is tapped
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                // Add ticket data to intent
                data.forEach { (key, value) ->
                    putExtra(key, value)
                }

                // Add action to open ticket details
                putExtra("action", "open_ticket")
                putExtra("ticketId", data["ticketId"])
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                notificationId.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val builder = NotificationCompat.Builder(context, AndroidNotificationService.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                try {
                    notify(notificationId.hashCode(), builder.build())
                } catch (e: SecurityException) {
                    // Handle missing notification permission
                }
            }
        }

        companion object {
            const val KEY_NOTIFICATION_ID = "notification_id"
            const val KEY_TITLE = "title"
            const val KEY_MESSAGE = "message"
        }
    }
