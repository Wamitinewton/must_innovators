package com.newton.events.di

import com.newton.core.network.NetworkConfiguration
import com.newton.database.db.AppDatabase
import com.newton.events.data.remote.EventApi
import com.newton.events.data.repository.EventRepositoryImpl
import com.newton.events.domain.repository.EventRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventRepositoryModule {
    @Provides
    @Singleton
    fun provideEventRepository(
        eventApi: EventApi,
        db: AppDatabase,
        networkConfiguration: NetworkConfiguration
    ): EventRepository = EventRepositoryImpl(eventApi, db, networkConfiguration)
}