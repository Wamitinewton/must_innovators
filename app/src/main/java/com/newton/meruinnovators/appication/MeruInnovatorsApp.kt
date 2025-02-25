package com.newton.meruinnovators.appication

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import coil3.ImageLoader
import com.newton.auth.data.work_manager.TokenRefreshWorkerFactory
import com.newton.auth.data.work_manager.scheduleTokenRefreshWork
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MeruInnovatorsApp: Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: TokenRefreshWorkerFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        WorkManager.initialize(this, workManagerConfiguration)
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