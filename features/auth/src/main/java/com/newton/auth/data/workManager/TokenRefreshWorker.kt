package com.newton.auth.data.workManager

import android.content.*
import androidx.hilt.work.*
import androidx.work.*
import coil3.network.*
import com.newton.core.domain.repositories.*
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
