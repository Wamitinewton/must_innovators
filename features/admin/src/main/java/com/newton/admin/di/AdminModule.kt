package com.newton.admin.di

import com.newton.admin.navigation.AdminNavigationApi
import com.newton.admin.navigation.AdminNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdminModule {
    @Provides
    @Singleton
    fun provideEventNavApi(): AdminNavigationApi {
        return AdminNavigationApiImpl()
    }
}