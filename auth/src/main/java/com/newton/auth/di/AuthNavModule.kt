package com.newton.auth.di

import com.newton.auth.navigation.AuthNavigationApi
import com.newton.auth.navigation.AuthNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AuthNavModule {

    @Provides
    fun provideAuthNavApi(): AuthNavigationApi {
        return AuthNavigationApiImpl()
    }
}