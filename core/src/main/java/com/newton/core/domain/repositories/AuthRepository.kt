package com.newton.core.domain.repositories

import com.newton.core.data.response.auth.OtpVerificationResponse
import com.newton.core.data.response.auth.RequestOtpResponse
import com.newton.core.domain.models.auth_models.DeleteAccount
import com.newton.core.domain.models.auth_models.LoginRequest
import com.newton.core.domain.models.auth_models.LoginResponse
import com.newton.core.domain.models.auth_models.LoginResultData
import com.newton.core.domain.models.auth_models.SignupRequest
import com.newton.core.domain.models.auth_models.SignupResponse
import com.newton.core.domain.models.auth_models.GetUserData
import com.newton.core.domain.models.auth_models.OtpRequest
import com.newton.core.domain.models.auth_models.ResetPasswordRequest
import com.newton.core.domain.models.auth_models.UserData
import com.newton.core.domain.models.auth_models.VerifyOtp
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

    suspend fun requestOtp(email: OtpRequest): Flow<Resource<RequestOtpResponse>>

    suspend fun verifyOtp(otp: VerifyOtp): Flow<Resource<OtpVerificationResponse>>

    suspend fun resetPassword(passwordRequest: ResetPasswordRequest): Flow<Resource<RequestOtpResponse>>

    suspend fun deleteAccount(): Flow<Resource<DeleteAccount>>

    suspend fun clearUserData()

    suspend fun logoutUser(): Flow<Resource<Unit>>

    fun observeLoggedInUser(): Flow<UserData?>

}
