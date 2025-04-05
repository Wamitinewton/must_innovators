package com.newton.home.di

import com.newton.home.navigation.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object HomeNavigationModule {
    @Provides
    @Singleton
    fun provideEventNavApi(): HomeNavigationApi {
        return HomeNavigationApiImpl()
    }
}
