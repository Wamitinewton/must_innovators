package com.newton.core.data.remote

import com.newton.core.data.Authenticated
import com.newton.core.data.response.auth.OtpVerificationResponse
import com.newton.core.data.response.auth.RequestOtpResponse
import com.newton.core.data.response.auth.UpdateProfileResponse
import com.newton.core.domain.models.auth_models.GetUserData
import com.newton.core.domain.models.auth_models.LoginRequest
import com.newton.core.domain.models.auth_models.LoginResponse
import com.newton.core.domain.models.auth_models.OtpRequest
import com.newton.core.domain.models.auth_models.ResetPasswordRequest
import com.newton.core.domain.models.auth_models.SignupRequest
import com.newton.core.domain.models.auth_models.SignupResponse
import com.newton.core.domain.models.auth_models.UpdateProfileRequest
import com.newton.core.domain.models.auth_models.VerifyOtp
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {
    @POST(ApiEndpoints.REGISTER)
    suspend fun signUp(
        @Body signUp: SignupRequest
    ): SignupResponse

    @POST(ApiEndpoints.LOGIN)
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST(ApiEndpoints.REFRESH_TOKEN)
    suspend fun refreshTokens(@Body refreshToken: String?): LoginResponse

    @Authenticated
    @GET(ApiEndpoints.GET_USER_DATA)
    suspend fun getUserData(): GetUserData

    @PATCH(ApiEndpoints.UPDATE_USER_DATA)
    suspend fun updateProfile(@Body updateProfileRequest: UpdateProfileRequest): UpdateProfileResponse

    @POST(ApiEndpoints.REQUEST_OTP)
    suspend fun requestOtp(@Body request: OtpRequest): RequestOtpResponse

    @POST(ApiEndpoints.VERIFY_OTP)
    suspend fun verifyOtp(@Body request: VerifyOtp): OtpVerificationResponse

    @POST(ApiEndpoints.RESET_PASSWORD)
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): RequestOtpResponse
}