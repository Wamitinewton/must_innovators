package com.newton.events.di

import com.newton.core.data.remote.*
import com.newton.core.domain.repositories.*
import com.newton.core.network.*
import com.newton.database.dao.*
import com.newton.database.db.*
import com.newton.events.data.repository.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import javax.inject.*

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
