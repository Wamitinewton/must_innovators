package com.newton.notifications.initializer

import android.content.Context
import androidx.startup.Initializer
import com.newton.notifications.manager.NotificationsManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent


class NotificationsInitializer : Initializer<Unit> {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface NotificationInitializerEntryPoint {
        fun notificationManager(): NotificationsManager
    }

    override fun create(context: Context) {
        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            NotificationInitializerEntryPoint::class.java
        )
        val notificationsManager = entryPoint.notificationManager()
        notificationsManager.initialize()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}