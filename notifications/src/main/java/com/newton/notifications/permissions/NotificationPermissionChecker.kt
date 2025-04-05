package com.newton.notifications.permissions

import android.content.*
import android.content.pm.*
import android.os.*
import androidx.core.app.*
import androidx.core.content.*

object NotificationPermissionChecker {
    /**
     * Check if app has notification permission
     */
    fun hasNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        }
    }
}
