package com.newton.meruinnovators.application

import android.app.Application
import android.os.Build
import androidx.work.Configuration
import coil3.ImageLoader
import com.google.firebase.FirebaseApp
import com.newton.auth.data.work_manager.TokenRefreshWorkerFactory
import com.newton.auth.data.work_manager.scheduleTokenRefreshWork
import com.newton.core.domain.repositories.AuthRepository
import com.newton.meruinnovators.BuildConfig
import com.newton.notifications.manager.NotificationsManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MeruInnovatorsApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: TokenRefreshWorkerFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var notificationsManager: NotificationsManager

    @Inject
    lateinit var authRepository: AuthRepository

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