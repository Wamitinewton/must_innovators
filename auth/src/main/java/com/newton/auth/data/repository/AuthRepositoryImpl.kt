package com.newton.auth.data.repository

import com.newton.auth.data.remote.authApiService.AuthService
import com.newton.auth.data.remote.mappers.toResponseData
import com.newton.auth.domain.models.sign_up.SignupRequest
import com.newton.auth.domain.models.sign_up.SignupResponse
import com.newton.auth.domain.repositories.AuthRepository
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
): AuthRepository {
    override suspend fun createUserWithEmailAndPassword(signupRequest: SignupRequest): Flow<Resource<SignupResponse>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = authService.signUp(signupRequest)
            if (response.message == "") {
                val user = response.data.toResponseData()
                emit(Resource.Success(data = user))
            } else {
                val message = response.data.nonFieldErrors?.first()
                emit(Resource.Error(message = message ?: "Unknown error occurred"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}