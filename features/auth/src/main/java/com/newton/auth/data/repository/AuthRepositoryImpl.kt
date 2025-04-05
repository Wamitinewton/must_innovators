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
package com.newton.auth.data.repository

import coil3.network.*
import com.newton.auth.data.dataStore.*
import com.newton.auth.data.tokenHolder.*
import com.newton.core.data.remote.*
import com.newton.core.data.response.auth.*
import com.newton.core.domain.models.authModels.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import com.newton.database.*
import com.newton.database.dao.*
import com.newton.database.mappers.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.*
import java.io.*
import javax.inject.*

class AuthRepositoryImpl
@Inject
constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager,
    private val userDao: UserDao,
    private val dbCleaner: DbCleaner
) : AuthRepository {
    init {
        try {
            AuthTokenHolder.initializeTokens(sessionManager)
        } catch (e: Exception) {
            Timber.e(e, "Failed to initialize tokens")
        }
    }

    override suspend fun createUserWithEmailAndPassword(
        signupRequest: SignupRequest
    ): Flow<Resource<SignupResponse>> =
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

    override suspend fun storeAuthTokens(
        accessToken: String,
        refreshToken: String
    ) {
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

    override suspend fun updateAuthTokens(
        accessToken: String,
        refreshToken: String
    ) {
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

    override suspend fun deleteAccount(): Flow<Resource<DeleteAccount>> =
        flow {
            emit(Resource.Loading(true))

            try {
                val response = authService.deleteAccount()
                emit(Resource.Success(response))
            } catch (e: Exception) {
                Timber.e(e, "Failed to delete account")
                emit(Resource.Error(e.message ?: "Failed to delete account"))
            } finally {
                emit(Resource.Loading(false))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun clearUserData() {
        sessionManager.clearTokens()
        dbCleaner.clearAllTables()
    }

    override suspend fun logoutUser(): Flow<Resource<Unit>> =
        flow {
            emit(Resource.Loading(true))

            AuthTokenHolder.accessToken = null
            AuthTokenHolder.refreshToken = null

            sessionManager.clearTokens()
            dbCleaner.clearAllTables()

            emit(Resource.Success(Unit))
        }.catch { e ->
            Timber.e(e, "Failed to logout user")
            emit(Resource.Error(e.message ?: "Failed to logout"))
        }.flowOn(Dispatchers.IO)

    override fun observeLoggedInUser(): Flow<UserData?> =
        userDao.observeUser().map { userEntity ->
            userEntity?.toAuthedUser()
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
