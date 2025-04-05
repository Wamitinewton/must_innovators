package com.newton.account.data.repository

import com.newton.core.data.remote.*
import com.newton.core.data.response.auth.*
import com.newton.core.domain.models.authModels.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import com.newton.database.dao.*
import com.newton.database.mappers.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class UpdateUserRepositoryImpl
@Inject
constructor(
    private val userDao: UserDao,
    private val authService: AuthService
) : UpdateUserRepository {
    override suspend fun updateUserProfile(
        updateProfileRequest: UpdateProfileRequest
    ): Flow<Resource<UpdateUserProfileResponse>> =
        safeApiCall {
            val response = authService.updateProfile(updateProfileRequest)
            updateLocalUserData(response.data)
            response
        }

    private suspend fun updateLocalUserData(userData: UserData) {
        val existingUser = userDao.getUser()
        if (existingUser == null) {
            userDao.insertUser(userData.toUserEntity())
            return
        }

        val updatedEntity = userData.toUserEntity().copy(id = existingUser.id)

        userDao.insertUser(updatedEntity)
    }
}
