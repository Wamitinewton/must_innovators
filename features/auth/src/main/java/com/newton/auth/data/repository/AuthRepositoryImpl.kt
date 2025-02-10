package com.newton.auth.data.repository

import coil3.network.HttpException
import com.newton.auth.data.data_store.SessionManager
import com.newton.auth.data.remote.authApiService.AuthService
import com.newton.auth.data.remote.mappers.toResponseData
import com.newton.auth.data.token_holder.AuthTokenHolder
import com.newton.auth.domain.models.login.LoginRequest
import com.newton.auth.domain.models.login.LoginResponse
import com.newton.auth.domain.models.login.LoginResultData
import com.newton.auth.domain.models.sign_up.SignupRequest
import com.newton.auth.domain.models.sign_up.SignupResponse
import com.newton.auth.domain.repositories.AuthRepository
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager
): AuthRepository {

    init {
        runBlocking {
            AuthTokenHolder.initializeTokens(sessionManager)
        }
    }

    override suspend fun createUserWithEmailAndPassword(signupRequest: SignupRequest): Flow<Resource<SignupResponse>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = authService.signUp(signupRequest)
            if (response.message == "") {
                val user = response.data.toResponseData()
                emit(Resource.Success(data = user))
                emit(Resource.Loading(false))
            } else {
                val message = response.data.nonFieldErrors?.first()
                emit(Resource.Error(message = message ?: "Unknown error occurred"))
                emit(Resource.Loading(false))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override suspend fun loginWithEmailAndPassword(loginRequest: LoginRequest): Flow<Resource<LoginResultData>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = authService.login(loginRequest)
            if (response.message == "") {
                val loggedInUser = response.data
                emit(Resource.Success(data = loggedInUser))
                emit(Resource.Loading(false))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error has occurred. Try again later"))
        }
    }

    override suspend fun refreshTokensFromServer(): LoginResponse? {
       return try {
           val refreshToken = getRefreshToken()
           val response = authService.refreshTokens(refreshToken)
           response
       } catch (e: HttpException) {
           Timber.e(message = "Connection Failed!")
           null
       }
    }

    override suspend fun storeAuthTokens(accessToken: String, refreshToken: String) {
        AuthTokenHolder.accessToken = accessToken
        AuthTokenHolder.refreshToken = refreshToken
        sessionManager.saveTokens(
            accessToken,
            refreshToken
        )

        // Debug: Check token persistence
        val savedAccessToken = sessionManager.fetchAccessToken()
        val savedRefreshToken = sessionManager.fetchRefreshToken()
        Timber.d("Tokens saved successfully: AccessToken=$savedAccessToken, RefreshToken=$savedRefreshToken")
    }

    override suspend fun updateAuthTokens(accessToken: String, refreshToken: String) {
        AuthTokenHolder.accessToken = accessToken
        AuthTokenHolder.refreshToken = refreshToken
        sessionManager.updateTokens(
            accessToken,
            refreshToken
        )
    }

    override fun getAccessToken(): String? {
        return AuthTokenHolder.accessToken
    }

    override fun getRefreshToken(): String? {
        return AuthTokenHolder.refreshToken
    }

}