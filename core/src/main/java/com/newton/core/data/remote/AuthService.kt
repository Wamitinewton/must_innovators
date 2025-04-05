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
