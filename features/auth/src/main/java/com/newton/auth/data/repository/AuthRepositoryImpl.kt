package com.newton.auth.data.repository

import coil3.network.HttpException
import com.newton.auth.data.data_store.SessionManager
import com.newton.auth.data.token_holder.AuthTokenHolder
import com.newton.core.data.remote.AuthService
import com.newton.core.data.response.auth.OtpVerificationResponse
import com.newton.core.data.response.auth.RequestOtpResponse
import com.newton.core.domain.models.auth_models.GetUserData
import com.newton.core.domain.models.auth_models.LoginRequest
import com.newton.core.domain.models.auth_models.LoginResponse
import com.newton.core.domain.models.auth_models.LoginResultData
import com.newton.core.domain.models.auth_models.OtpRequest
import com.newton.core.domain.models.auth_models.ResetPasswordRequest
import com.newton.core.domain.models.auth_models.SignupRequest
import com.newton.core.domain.models.auth_models.SignupResponse
import com.newton.core.domain.models.auth_models.UserData
import com.newton.core.domain.models.auth_models.VerifyOtp
import com.newton.core.domain.repositories.AuthRepository
import com.newton.core.utils.Resource
import com.newton.core.utils.safeApiCall
import com.newton.database.dao.UserDao
import com.newton.database.mappers.toAuthedUser
import com.newton.database.mappers.toUserEntity
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager,
    private val userDao: UserDao,
) : AuthRepository {

    init {
        try {
            AuthTokenHolder.initializeTokens(sessionManager)
        } catch (e: Exception) {
            Timber.e(e, "Failed to initialize tokens")
        }
    }

    override suspend fun createUserWithEmailAndPassword(signupRequest: SignupRequest): Flow<Resource<SignupResponse>> =
        safeApiCall {
            authService.signUp(signupRequest)
        }

    override suspend fun loginWithEmailAndPassword(loginRequest: LoginRequest): Flow<Resource<LoginResultData>> =
        safeApiCall {
            authService.login(loginRequest).data
        }

    override suspend fun refreshTokensFromServer(): LoginResponse? {
        return try {
            val refreshToken =
                getRefreshToken() ?: throw TokenRefreshException("No refresh token available")
            val response = authService.refreshTokens(refreshToken)

            response
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

            verifyTokenPersistence()
        } catch (e: Exception) {
            Timber.e(e, "Failed to update auth tokens")
            throw TokenStorageException("Failed to update authentication tokens", e)
        }
    }

    override suspend fun getUserData(): Flow<Resource<GetUserData>> =
        safeApiCall {
            authService.getUserData()
        }

    override fun getAccessToken(): String? = sessionManager.fetchAccessToken()

    override fun getRefreshToken(): String? = sessionManager.fetchRefreshToken()
    override suspend fun getLoggedInUser(): UserData? {
        return userDao.getUser()?.toAuthedUser()
    }

    override suspend fun storeLoggedInUser(userData: UserData) {
        return userDao.insertUser(userData.toUserEntity())
    }

    override suspend fun requestOtp(email: OtpRequest): Flow<Resource<RequestOtpResponse>> =
        safeApiCall {
            authService.requestOtp(email)
        }

    override suspend fun verifyOtp(otp: VerifyOtp): Flow<Resource<OtpVerificationResponse>> =
        safeApiCall {
            authService.verifyOtp(otp)
        }

    override suspend fun resetPassword(passwordRequest: ResetPasswordRequest): Flow<Resource<RequestOtpResponse>> =
        safeApiCall {
            authService.resetPassword(passwordRequest)
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
    private class TokenStorageException(message: String, cause: Throwable? = null) :
        Exception(message, cause)

}