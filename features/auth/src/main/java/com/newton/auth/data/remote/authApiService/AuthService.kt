package com.newton.auth.data.remote.authApiService

import com.newton.auth.data.remote.authResponse.sign_up.SignupResponseDto
import com.newton.auth.domain.models.login.LoginRequest
import com.newton.auth.domain.models.login.LoginResponse
import com.newton.auth.domain.models.sign_up.SignupRequest
import com.newton.auth.domain.models.sign_up.SignupResponse
import retrofit2.http.Body
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
}