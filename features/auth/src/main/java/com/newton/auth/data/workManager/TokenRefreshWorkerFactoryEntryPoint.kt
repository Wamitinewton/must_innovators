package com.newton.auth.data.workManager

import android.content.*
import androidx.work.*
import com.newton.core.domain.repositories.*
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
