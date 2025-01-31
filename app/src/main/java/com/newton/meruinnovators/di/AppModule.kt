package com.newton.meruinnovators.di

import com.newton.auth.navigation.AuthNavigationApi
import com.newton.meruinnovators.navigation.NavigationSubGraphs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideNavigationSubGraphs(
        authNavigationApi: AuthNavigationApi
    ): NavigationSubGraphs {
        return NavigationSubGraphs(authNavigationApi)
    }
}