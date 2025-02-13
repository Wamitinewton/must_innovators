package com.newton.auth.data.remote.authApiService

import com.newton.auth.di.Authenticated
import com.newton.auth.domain.models.get_user.GetUserData
import com.newton.auth.domain.models.login.LoginRequest
import com.newton.auth.domain.models.login.LoginResponse
import com.newton.auth.domain.models.sign_up.SignupRequest
import com.newton.auth.domain.models.sign_up.SignupResponse
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