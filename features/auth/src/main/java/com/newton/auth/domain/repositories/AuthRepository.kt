package com.newton.auth.domain.repositories

import com.newton.auth.domain.models.get_user.GetUserData
import com.newton.auth.domain.models.login.LoginRequest
import com.newton.auth.domain.models.login.LoginResponse
import com.newton.auth.domain.models.login.LoginResultData
import com.newton.auth.domain.models.sign_up.SignupRequest
import com.newton.auth.domain.models.sign_up.SignupResponse
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun createUserWithEmailAndPassword(signupRequest: SignupRequest): Flow<Resource<SignupResponse>>

    suspend fun loginWithEmailAndPassword(loginRequest: LoginRequest): Flow<Resource<LoginResultData>>

    suspend fun refreshTokensFromServer(): LoginResponse?

    suspend fun storeAuthTokens(accessToken: String, refreshToken: String)

    suspend fun updateAuthTokens(accessToken: String, refreshToken: String)

    suspend fun getUserData(): Flow<Resource<GetUserData>>

    fun getAccessToken(): String?

    fun getRefreshToken(): String?
}
