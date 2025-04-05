package com.newton.core.data.remote

import com.newton.core.data.*
import com.newton.core.data.response.auth.*
import com.newton.core.domain.models.authModels.*
import retrofit2.http.*

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
    suspend fun refreshTokens(
        @Body refreshToken: String?
    ): LoginResponse

    @AuthRunTime
    @GET(ApiEndpoints.GET_USER_DATA)
    suspend fun getUserData(): GetUserData

    @PATCH(ApiEndpoints.UPDATE_USER_DATA)
    suspend fun updateProfile(
        @Body updateProfileRequest: UpdateProfileRequest
    ): UpdateUserProfileResponse

    @POST(ApiEndpoints.REQUEST_OTP)
    suspend fun requestOtp(
        @Body request: OtpRequest
    ): RequestOtpResponse

    @POST(ApiEndpoints.VERIFY_OTP)
    suspend fun verifyOtp(
        @Body request: VerifyOtp
    ): OtpVerificationResponse

    @POST(ApiEndpoints.RESET_PASSWORD)
    suspend fun resetPassword(
        @Body resetPasswordRequest: ResetPasswordRequest
    ): RequestOtpResponse

    @AuthRunTime
    @DELETE(ApiEndpoints.DELETE_ACCOUNT)
    suspend fun deleteAccount(): DeleteAccount
}
