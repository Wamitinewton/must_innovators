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
import androidx.hilt.work.*
import androidx.work.*
import coil3.network.*
import com.newton.network.domain.repositories.*
import dagger.assisted.*
import timber.log.*

@HiltWorker
class TokenRefreshWorker
@AssistedInject
constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val authRepository: AuthRepository
) : CoroutineWorker(appContext, workerParameters) {
    companion object {
        const val TOKEN_WORK_NAME = "TokenRefreshWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            Timber.d("Refreshing Tokens. Kindly wait up")
            val freshTokens = authRepository.refreshTokensFromServer()

            freshTokens?.let {
                authRepository.updateAuthTokens(
                    accessToken = it.data.access,
                    refreshToken = it.data.refresh
                )
            }

            Timber.d("Tokens refresh successful")
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
