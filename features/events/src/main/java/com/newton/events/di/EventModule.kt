package com.newton.events.di

import com.newton.events.navigation.EventsNavigationApi
import com.newton.events.navigation.EventsNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object EventNavModule {

    @Provides
    fun provideEventNavApi(): EventsNavigationApi {
        return EventsNavigationApiImpl()
    }
}