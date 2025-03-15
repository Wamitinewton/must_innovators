package com.newton.events.di

import com.newton.core.data.remote.EventService
import com.newton.events.navigation.EventsNavigationApi
import com.newton.events.navigation.EventsNavigationApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object EventModule {

    @Provides
    @Singleton
    fun provideEventNavApi(): EventsNavigationApi {
        return EventsNavigationApiImpl()
    }

    @Provides
    @Singleton
    fun providesEventApi(retrofit: Retrofit): EventService {
        return retrofit.create(EventService::class.java)
    }


}