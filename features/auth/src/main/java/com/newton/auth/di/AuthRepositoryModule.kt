package com.newton.auth.di

import com.newton.auth.data.dataStore.*
import com.newton.auth.data.repository.*
import com.newton.core.data.remote.*
import com.newton.core.domain.repositories.*
import com.newton.database.*
import com.newton.database.dao.*
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
        dbCleaner: DbCleaner,
        userDao: UserDao
    ): AuthRepository = AuthRepositoryImpl(authService, sessionManager, userDao, dbCleaner)
}
