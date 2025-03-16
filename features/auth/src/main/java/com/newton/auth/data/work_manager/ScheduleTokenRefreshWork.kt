package com.newton.auth.data.work_manager

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleTokenRefreshWork(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    val tokenRefreshWorkRequest =
        PeriodicWorkRequestBuilder<TokenRefreshWorker>(10, TimeUnit.DAYS)
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