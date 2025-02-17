package com.newton.home.di

import com.newton.blogs.navigation.BlogsNavigationApi
import com.newton.blogs.navigation.BlogsNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BlogsModule {
    @Provides
    @Singleton
    fun provideEventNavApi(): BlogsNavigationApi {
        return BlogsNavigationApiImpl()
    }
}