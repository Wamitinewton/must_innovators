package com.newton.auth.di

import com.newton.auth.data.remote.authApiService.AuthService
import com.newton.auth.data.repository.AuthRepositoryImpl
import com.newton.auth.domain.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        networkService: AuthService
    ): AuthRepository = AuthRepositoryImpl(networkService)
}