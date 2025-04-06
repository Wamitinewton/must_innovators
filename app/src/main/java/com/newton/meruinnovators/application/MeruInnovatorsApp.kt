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
package com.newton.meruinnovators.application

import android.app.*
import androidx.work.*
import coil3.*
import com.google.firebase.*
import com.newton.auth.data.workManager.*
import com.newton.core.domain.repositories.*
import com.newton.meruinnovators.BuildConfig
import com.newton.notifications.manager.*
import dagger.hilt.android.*
import kotlinx.coroutines.*
import timber.log.*
import javax.inject.*

@HiltAndroidApp
class MeruInnovatorsApp : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: TokenRefreshWorkerFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var notificationsManager: NotificationsManager

    @Inject
    lateinit var authRepository: com.newton.network.domain.repositories.AuthRepository

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        FirebaseApp.initializeApp(this)
        notificationsManager.checkNotificationPermission()

        scheduleTokenRefreshWork(this)
    }

    fun newImageLoader(): ImageLoader = imageLoader

    override val workManagerConfiguration: Configuration by lazy(LazyThreadSafetyMode.PUBLICATION) {
        Configuration.Builder()
            .setExecutor(Dispatchers.Default.asExecutor())
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
    }
}
