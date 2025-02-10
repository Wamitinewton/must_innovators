package com.newton.events.di

import com.newton.events.data.remote.EventApi
import com.newton.events.data.repository.EventRepositoryImpl
import com.newton.events.domain.repository.EventRepository
import com.newton.events.domain.usecases.EventUseCase
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
    fun providesEventApi(retrofit: Retrofit): EventApi{
        return retrofit.create(EventApi::class.java)
    }

    @Provides
    @Singleton
    fun providesEventRepository(api:EventApi):EventRepository{
        return EventRepositoryImpl(
            api
        )
    }

    @Provides
    @Singleton
    fun provideEventUseCase(repository: EventRepository): EventUseCase{
        return EventUseCase(repository)
    }

}