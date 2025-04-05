package com.newton.auth.di

import com.newton.core.data.remote.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import retrofit2.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object AuthServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}
