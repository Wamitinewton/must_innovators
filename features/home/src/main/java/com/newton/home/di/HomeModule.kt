package com.newton.home.di

import com.newton.events.navigation.HomeNavigationApi
import com.newton.events.navigation.HomeNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Provides
    @Singleton
    fun provideEventNavApi(): HomeNavigationApi {
        return HomeNavigationApiImpl()
    }
}