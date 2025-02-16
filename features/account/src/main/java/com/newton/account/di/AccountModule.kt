package com.newton.account.di

import com.newton.account.navigation.AccountNavigationApi
import com.newton.events.navigation.AccountNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {
    @Provides
    @Singleton
    fun provideEventNavApi(): AccountNavigationApi {
        return AccountNavigationApiImpl()
    }
}