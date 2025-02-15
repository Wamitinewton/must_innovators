package com.newton.auth.data.remote.authApiService

import com.newton.auth.di.Authenticated
import com.newton.core.domain.models.auth_models.LoginRequest
import com.newton.core.domain.models.auth_models.LoginResponse
import com.newton.core.domain.models.auth_models.SignupRequest
import com.newton.core.domain.models.auth_models.SignupResponse
import com.newton.core.domain.models.auth_models.GetUserData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST(AuthApiEndpoints.REGISTER)
    suspend fun signUp(
        @Body signUp: SignupRequest
    ): SignupResponse

    @POST(AuthApiEndpoints.LOGIN)
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST(AuthApiEndpoints.REFRESH_TOKEN)
    suspend fun refreshTokens(@Body refreshToken: String?): LoginResponse

    @Authenticated
    @GET(AuthApiEndpoints.GET_USER_DATA)
    suspend fun getUserData(): GetUserData
}