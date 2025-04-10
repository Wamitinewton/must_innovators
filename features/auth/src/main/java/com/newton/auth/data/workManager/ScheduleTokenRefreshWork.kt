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
        PeriodicWorkRequestBuilder<TokenRefreshWorker>(50, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                10,
                TimeUnit.MINUTES
            )
            .build()

    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork(
            TokenRefreshWorker.TOKEN_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            tokenRefreshWorkRequest
        )
}
