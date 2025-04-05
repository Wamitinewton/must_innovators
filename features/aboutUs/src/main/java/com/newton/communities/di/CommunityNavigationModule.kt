package com.newton.communities.di

import com.newton.communities.navigation.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object CommunityNavigationModule {
    @Provides
    @Singleton
    fun provideCommunityApi(): CommunityNavigationApi = CommunityNavigationImpl()
}
