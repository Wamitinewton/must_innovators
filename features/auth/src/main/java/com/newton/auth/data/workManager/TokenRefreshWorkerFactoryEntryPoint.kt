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
import com.newton.network.domain.repositories.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

@EntryPoint
@InstallIn(SingletonComponent::class)
interface TokenRefreshWorkerFactoryEntryPoint {
    fun authRepository(): AuthRepository
}

class TokenRefreshWorkerFactory
@Inject
constructor() : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val workerFactoryEntryPoint =
            EntryPoints.get(appContext, TokenRefreshWorkerFactoryEntryPoint::class.java)
        val authRepository = workerFactoryEntryPoint.authRepository()

        return when (workerClassName) {
            TokenRefreshWorker::class.java.name ->
                TokenRefreshWorker(
                    appContext,
                    workerParameters,
                    authRepository
                )

            else -> null
        }
    }
}
