package com.newton.auth.data.work_manager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import coil3.network.HttpException
import com.newton.core.domain.repositories.AuthRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class TokenRefreshWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParameters: WorkerParameters,
    private val authRepository: AuthRepository
): CoroutineWorker(appContext, workerParameters) {

    companion object{
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