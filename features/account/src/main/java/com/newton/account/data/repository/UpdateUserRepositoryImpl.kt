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
package com.newton.account.data.repository

import com.newton.database.dao.*
import com.newton.database.mappers.*
import com.newton.network.*
import com.newton.network.data.remote.*
import com.newton.network.data.dto.auth.*
import com.newton.network.domain.models.authModels.*
import com.newton.network.domain.repositories.*
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
