package com.newton.auth.data.repository

import coil3.network.HttpException
import com.newton.auth.data.data_store.SessionManager
import com.newton.auth.data.remote.authApiService.AuthService
import com.newton.auth.data.token_holder.AuthTokenHolder
import com.newton.auth.domain.models.login.LoginRequest
import com.newton.auth.domain.models.login.LoginResponse
import com.newton.auth.domain.models.login.LoginResultData
import com.newton.auth.domain.models.sign_up.SignupRequest
import com.newton.auth.domain.models.sign_up.SignupResponse
import com.newton.auth.domain.repositories.AuthRepository
import com.newton.core.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException as RetrofitHttpException

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager
) : AuthRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                AuthTokenHolder.initializeTokens(sessionManager)
            } catch (e: Exception) {
                Timber.e(e, "Failed to initialize tokens")
            }
        }
    }

    override suspend fun createUserWithEmailAndPassword(signupRequest: SignupRequest): Flow<Resource<SignupResponse>> = flow {
        try {
            emit(Resource.Loading(true))
            val response = authService.signUp(signupRequest)

            emit(Resource.Success(data = response))
        } catch (e: RetrofitHttpException) {
            emit(Resource.Error(message = handleHttpError(e)))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Network error: Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "An unexpected error occurred: ${e.message}"))
        } finally {
            emit(Resource.Loading(false))
        }
    }

    override suspend fun loginWithEmailAndPassword(loginRequest: LoginRequest): Flow<Resource<LoginResultData>> = flow {
        try {
            emit(Resource.Loading(true))
            val response = authService.login(loginRequest)
            emit(Resource.Success(data = response.data))
        } catch (e: RetrofitHttpException) {
            emit(Resource.Error(message = handleHttpError(e)))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Network error: Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "An unexpected error occurred: ${e.message}"))
        } finally {
            emit(Resource.Loading(false))
        }
    }

    override suspend fun refreshTokensFromServer(): LoginResponse? {
        return try {
            val refreshToken = getRefreshToken() ?: throw TokenRefreshException("No refresh token available")
            val response = authService.refreshTokens(refreshToken)

            if (response.message.isEmpty()) {
                response
            } else {
                throw TokenRefreshException("Token refresh failed: ${response.message}")
            }
        } catch (e: HttpException) {
            Timber.e(e, "HTTP error during token refresh")
            null
        } catch (e: IOException) {
            Timber.e(e, "Network error during token refresh")
            null
        } catch (e: TokenRefreshException) {
            Timber.e(e, "Token refresh failed")
            null
        } catch (e: Exception) {
            Timber.e(e, "Unexpected error during token refresh")
            null
        }
    }

    override suspend fun storeAuthTokens(accessToken: String, refreshToken: String) {
        try {
            AuthTokenHolder.accessToken = accessToken
            AuthTokenHolder.refreshToken = refreshToken
            sessionManager.saveTokens(accessToken, refreshToken)

            // Verify token persistence
            verifyTokenPersistence()
        } catch (e: Exception) {
            Timber.e(e, "Failed to store auth tokens")
            throw TokenStorageException("Failed to store authentication tokens", e)
        }
    }

    override suspend fun updateAuthTokens(accessToken: String, refreshToken: String) {
        try {
            AuthTokenHolder.accessToken = accessToken
            AuthTokenHolder.refreshToken = refreshToken
            sessionManager.updateTokens(accessToken, refreshToken)

            // Verify token persistence
            verifyTokenPersistence()
        } catch (e: Exception) {
            Timber.e(e, "Failed to update auth tokens")
            throw TokenStorageException("Failed to update authentication tokens", e)
        }
    }

    override fun getAccessToken(): String? = AuthTokenHolder.accessToken

    override fun getRefreshToken(): String? = AuthTokenHolder.refreshToken

    private fun handleHttpError(error: RetrofitHttpException): String {
        return when (error.code()) {
            400 -> "Invalid request: Please check your input"
            401 -> "Unauthorized: Please log in again"
            403 -> "Access denied"
            404 -> "Resource not found"
            500, 502, 503 -> "Server error: Please try again later"
            else -> "Network error: ${error.message()}"
        }
    }

    private fun verifyTokenPersistence() {
        val savedAccessToken = sessionManager.fetchAccessToken()
        val savedRefreshToken = sessionManager.fetchRefreshToken()

        if (savedAccessToken != AuthTokenHolder.accessToken || savedRefreshToken != AuthTokenHolder.refreshToken) {
            Timber.e("Token persistence verification failed")
            throw TokenStorageException("Token persistence verification failed")
        }

        Timber.d("Tokens saved successfully: AccessToken=$savedAccessToken, RefreshToken=$savedRefreshToken")
    }

    private class TokenRefreshException(message: String) : Exception(message)
    private class TokenStorageException(message: String, cause: Throwable? = null) : Exception(message, cause)
}