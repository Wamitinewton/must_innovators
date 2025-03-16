package com.newton.account.data.repository

import com.newton.core.data.remote.AuthService
import com.newton.core.data.response.auth.UpdateProfileResponse
import com.newton.core.domain.models.auth_models.UpdateProfileRequest
import com.newton.core.domain.models.auth_models.UserData
import com.newton.core.domain.repositories.UpdateUserRepository
import com.newton.core.utils.Resource
import com.newton.core.utils.safeApiCall
import com.newton.database.dao.UserDao
import com.newton.database.mappers.toUserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val authService: AuthService
) : UpdateUserRepository {

    override suspend fun updateUserProfile(updateProfileRequest: UpdateProfileRequest): Flow<Resource<UpdateProfileResponse>> =
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