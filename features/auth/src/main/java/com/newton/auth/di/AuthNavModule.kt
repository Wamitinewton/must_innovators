package com.newton.auth.di

import com.newton.auth.navigation.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*

@InstallIn(SingletonComponent::class)
@Module
object AuthNavModule {
    @Provides
    fun provideAuthNavApi(): AuthNavigationApi {
        return AuthNavigationApiImpl()
    }
}
