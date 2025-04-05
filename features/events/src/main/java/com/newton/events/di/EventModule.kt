package com.newton.events.di

import com.newton.core.data.remote.*
import com.newton.events.navigation.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import retrofit2.*
import javax.inject.*

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
