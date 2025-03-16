package com.newton.core.domain.repositories

import com.newton.core.data.response.auth.UpdateProfileResponse
import com.newton.core.domain.models.auth_models.UpdateProfileRequest
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UpdateUserRepository {

    suspend fun updateUserProfile(updateProfileRequest: UpdateProfileRequest): Flow<Resource<UpdateProfileResponse>>
}