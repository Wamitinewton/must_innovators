package com.newton.auth.data.workManager

import android.content.*
import androidx.work.*
import java.util.concurrent.*

fun scheduleTokenRefreshWork(context: Context) {
    val constraints =
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

    val tokenRefreshWorkRequest =
        PeriodicWorkRequestBuilder<TokenRefreshWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                1,
                TimeUnit.HOURS
            )
            .build()

    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork(
            TokenRefreshWorker.TOKEN_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            tokenRefreshWorkRequest
        )
}
