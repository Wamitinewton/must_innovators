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
