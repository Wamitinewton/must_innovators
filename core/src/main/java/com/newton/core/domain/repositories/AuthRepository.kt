package com.newton.core.domain.repositories

import com.newton.core.domain.models.auth_models.LoginRequest
import com.newton.core.domain.models.auth_models.LoginResponse
import com.newton.core.domain.models.auth_models.LoginResultData
import com.newton.core.domain.models.auth_models.SignupRequest
import com.newton.core.domain.models.auth_models.SignupResponse
import com.newton.core.domain.models.auth_models.GetUserData
import com.newton.core.domain.models.auth_models.UserData
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

    suspend fun getLoggedInUser(): UserData?

    suspend fun storeLoggedInUser(userData: UserData)


}
