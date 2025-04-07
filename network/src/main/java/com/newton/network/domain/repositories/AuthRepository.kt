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
package com.newton.network.domain.repositories

import com.newton.network.data.response.auth.*
import com.newton.network.domain.models.authModels.*
import kotlinx.coroutines.flow.*

interface AuthRepository {
    suspend fun createUserWithEmailAndPassword(signupRequest: SignupRequest): Flow<com.newton.network.Resource<SignupResponse>>

    suspend fun loginWithEmailAndPassword(loginRequest: LoginRequest): Flow<com.newton.network.Resource<LoginResultData>>

    suspend fun refreshTokensFromServer(): LoginResponse?

    suspend fun storeAuthTokens(
        accessToken: String,
        refreshToken: String
    )

    suspend fun updateAuthTokens(
        accessToken: String,
        refreshToken: String
    )

    suspend fun getUserData(): Flow<com.newton.network.Resource<GetUserData>>

    fun getAccessToken(): String?

    fun getRefreshToken(): String?

    suspend fun getLoggedInUser(): UserData?

    suspend fun storeLoggedInUser(userData: UserData)

    suspend fun requestOtp(email: OtpRequest): Flow<com.newton.network.Resource<RequestOtpResponse>>

    suspend fun verifyOtp(otp: VerifyOtp): Flow<com.newton.network.Resource<OtpVerificationResponse>>

    suspend fun resetPassword(passwordRequest: ResetPasswordRequest): Flow<com.newton.network.Resource<RequestOtpResponse>>

    suspend fun deleteAccount(): Flow<com.newton.network.Resource<DeleteAccount>>

    suspend fun clearUserData()

    suspend fun logoutUser(): Flow<com.newton.network.Resource<Unit>>

    fun observeLoggedInUser(): Flow<UserData?>
}
