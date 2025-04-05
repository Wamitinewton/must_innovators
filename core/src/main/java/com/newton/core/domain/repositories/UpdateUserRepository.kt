package com.newton.core.domain.repositories

import com.newton.core.data.response.auth.*
import com.newton.core.domain.models.authModels.*
import com.newton.core.utils.*
import kotlinx.coroutines.flow.*

interface UpdateUserRepository {
    suspend fun updateUserProfile(updateProfileRequest: UpdateProfileRequest): Flow<Resource<UpdateUserProfileResponse>>
}
