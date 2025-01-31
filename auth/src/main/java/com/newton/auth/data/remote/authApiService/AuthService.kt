package com.newton.auth.data.remote.authApiService

import com.newton.auth.data.remote.authResponse.sign_up.SignupResponseDto
import com.newton.auth.domain.models.sign_up.SignupRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST(AuthApiEndpoints.REGISTER)
    suspend fun signUp(
        @Body signUp: SignupRequest
    ): SignupResponseDto
}