/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
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
