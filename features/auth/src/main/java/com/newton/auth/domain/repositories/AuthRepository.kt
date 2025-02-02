package com.newton.auth.domain.repositories

import com.newton.auth.domain.models.sign_up.SignupRequest
import com.newton.auth.domain.models.sign_up.SignupResponse
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun createUserWithEmailAndPassword(signupRequest: SignupRequest): Flow<Resource<SignupResponse>>
}