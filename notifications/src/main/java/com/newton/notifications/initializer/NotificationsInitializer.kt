package com.newton.notifications.initializer

import android.content.*
import androidx.startup.*
import com.newton.notifications.manager.*
import dagger.hilt.*
import dagger.hilt.android.*
import dagger.hilt.components.*

class NotificationsInitializer : Initializer<Unit> {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface NotificationInitializerEntryPoint {
        fun notificationManager(): NotificationsManager
    }

    override fun create(context: Context) {
        val entryPoint =
            EntryPointAccessors.fromApplication(
                context.applicationContext,
                NotificationInitializerEntryPoint::class.java
            )
        val notificationsManager = entryPoint.notificationManager()
        notificationsManager.initialize()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
