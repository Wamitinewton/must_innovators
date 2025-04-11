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
package com.newton.network.data.remote

import com.newton.network.*
import com.newton.network.data.response.auth.*
import retrofit2.http.*

interface AuthService {
    @POST(ApiEndpoints.REGISTER)
    suspend fun signUp(
        @Body signUp: com.newton.network.domain.models.authModels.SignupRequest
    ): com.newton.network.domain.models.authModels.SignupResponse

    @POST(ApiEndpoints.LOGIN)
    suspend fun login(
        @Body loginRequest: com.newton.network.domain.models.authModels.LoginRequest
    ): com.newton.network.domain.models.authModels.LoginResponse

    @POST(ApiEndpoints.REFRESH_TOKEN)
    suspend fun refreshTokens(
        @Body refreshToken: String?
    ): com.newton.network.domain.models.authModels.LoginResponse

    @AuthRunTime
    @GET(ApiEndpoints.GET_USER_DATA)
    suspend fun getUserData(): com.newton.network.domain.models.authModels.GetUserData

    @PATCH(ApiEndpoints.UPDATE_USER_DATA)
    suspend fun updateProfile(
        @Body updateProfileRequest: com.newton.network.domain.models.authModels.UpdateProfileRequest
    ): UpdateUserProfileResponse

    @POST(ApiEndpoints.REQUEST_OTP)
    suspend fun requestOtp(
        @Body request: com.newton.network.domain.models.authModels.OtpRequest
    ): RequestOtpResponse

    @POST(ApiEndpoints.VERIFY_OTP)
    suspend fun verifyOtp(
        @Body request: com.newton.network.domain.models.authModels.VerifyOtp
    ): OtpVerificationResponse

    @POST(ApiEndpoints.RESET_PASSWORD)
    suspend fun resetPassword(
        @Body resetPasswordRequest: com.newton.network.domain.models.authModels.ResetPasswordRequest
    ): RequestOtpResponse

    @AuthRunTime
    @DELETE(ApiEndpoints.DELETE_ACCOUNT)
    suspend fun deleteAccount()
}
