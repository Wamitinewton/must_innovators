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
package com.newton.auth.di

import com.newton.auth.data.repository.*
import com.newton.database.dao.*
import com.newton.database.dbManager.*
import com.newton.network.data.dataStore.SessionManager
import com.newton.network.data.remote.*
import com.newton.network.domain.repositories.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService,
        sessionManager: SessionManager,
        roomDataManager: RoomDataManager,
        userDao: UserDao
    ): AuthRepository = AuthRepositoryImpl(authService, sessionManager, userDao, roomDataManager)
}
