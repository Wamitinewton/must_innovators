package com.newton.home.di

import com.newton.home.navigation.HomeNavigationApi
import com.newton.home.navigation.HomeNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeNavigationModule {
    @Provides
    @Singleton
    fun provideEventNavApi(): HomeNavigationApi {
        return HomeNavigationApiImpl()
    }
}