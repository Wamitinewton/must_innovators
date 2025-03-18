package com.newton.events.di

import com.newton.core.network.NetworkConfiguration
import com.newton.database.dao.EventDao
import com.newton.database.dao.TicketDao
import com.newton.database.db.AppDatabase
import com.newton.core.data.remote.EventService
import com.newton.events.data.repository.EventRepositoryImpl
import com.newton.core.domain.repositories.EventRepository
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
        eventApi: EventService,
        db: AppDatabase,
        networkConfiguration: NetworkConfiguration,
        ticketDao: TicketDao,
        eventDao: EventDao
    ): EventRepository = EventRepositoryImpl(eventApi, db, ticketDao, eventDao)

}