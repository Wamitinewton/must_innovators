package com.newton.events.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.newton.core.domain.models.event_models.EventsData
import com.newton.core.utils.Resource
import com.newton.database.dao.EventDao
import com.newton.database.dao.EventRemoteKeysDao
import com.newton.database.db.AppDatabase
import com.newton.events.data.mappers.EventMappers.toDomainEvent
import com.newton.events.data.paging.EventRemoteMediator
import com.newton.events.data.remote.EventApi
import com.newton.events.domain.models.Event
import com.newton.events.domain.models.EventRequest
import com.newton.events.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val api: EventApi,
    private val db: AppDatabase,
    private val eventDao: EventDao,
    private val remoteKeysDao: EventRemoteKeysDao
) : EventRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedEvents(): Flow<PagingData<EventsData>> {
        val pagingSourceFactory = { db.eventDao.getEventsByPage() }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            remoteMediator = EventRemoteMediator(
                db, api,
                eventDao,
                remoteKeysDao
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainEvent() }
        }
    }



    override suspend fun getEventById(id: Int): Flow<Resource<Event>> = flow {

    }



    override suspend fun updateEvent(id: Int, request: EventRequest): Flow<Resource<Event>> = flow {

    }

    override suspend fun deleteEvent(id: Int): Flow<Resource<String>> = flow {
        emit(Resource.Loading(true))
        try {


        } catch (e: Exception) {
            throw Exception(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun createEvent(request: EventRequest): Flow<Resource<Event>> = flow {

    }


    private fun <T> handleError(exception: Throwable): Resource<T> {
        return when (exception) {

            is IOException -> Resource.Error(
                "IO error: ${exception.message ?: "Unknown IO error"}"
            )

            else -> Resource.Error(
                exception.message ?: "Unknown error occurred"
            )
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 6
    }
}

