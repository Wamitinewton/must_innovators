package com.newton.account.data.repository

import com.newton.core.data.remote.AuthService
import com.newton.core.data.response.auth.UpdateProfileResponse
import com.newton.core.domain.models.auth_models.UpdateProfileRequest
import com.newton.core.domain.models.auth_models.UserData
import com.newton.core.domain.repositories.UpdateUserRepository
import com.newton.core.utils.Resource
import com.newton.database.dao.UserDao
import com.newton.database.mappers.toUserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateUserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val authService: AuthService
) : UpdateUserRepository {

    override suspend fun updateUserProfile(updateProfileRequest: UpdateProfileRequest): Flow<Resource<UpdateProfileResponse>> =
        flow {
            emit(Resource.Loading(true))

            try {

                val response = authService.updateProfile(updateProfileRequest)

                updateLocalUserData(response.data)

                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(message = handleError(e)))
            } finally {
                emit(Resource.Loading(false))
            }
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

    private fun handleError(error: Exception): String = when (error) {
        is HttpException -> when (error.code()) {
            400 -> "Invalid request: Please check your input"
            401 -> "Unauthorized: Please log in again"
            403 -> "Access denied"
            404 -> "Resource not found"
            in 500..599 -> "Server error: Please try again later"
            else -> "Network error: ${error.message()}"
        }

        is IOException -> "Network error: Please check your internet connection"
        else -> "An unexpected error occurred: ${error.message}"
    }
}