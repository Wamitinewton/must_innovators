package com.newton.auth.di

import com.newton.auth.data.data_store.SessionManager
import com.newton.auth.data.repository.AuthRepositoryImpl
import com.newton.core.data.remote.AuthService
import com.newton.core.domain.repositories.AuthRepository
import com.newton.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService,
        sessionManager: SessionManager,
        userDao: UserDao,
    ): AuthRepository = AuthRepositoryImpl(authService, sessionManager, userDao)
}