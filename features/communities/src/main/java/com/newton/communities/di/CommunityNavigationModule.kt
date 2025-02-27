package com.newton.communities.di

import com.newton.communities.navigation.CommunityNavigationApi
import com.newton.communities.navigation.CommunityNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommunityNavigationModule {
    @Provides
    @Singleton
    fun provideCommunityApi(): CommunityNavigationApi{
        return CommunityNavigationImpl()
    }
}