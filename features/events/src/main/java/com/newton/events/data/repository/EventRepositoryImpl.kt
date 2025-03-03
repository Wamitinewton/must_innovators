package com.newton.events.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.util.query
import com.newton.core.domain.models.event_models.EventsData
import com.newton.core.domain.models.event_models.RegistrationResponse
import com.newton.core.network.NetworkConfiguration
import com.newton.core.utils.Resource
import com.newton.database.dao.EventDao
import com.newton.database.dao.TicketDao
import com.newton.database.db.AppDatabase
import com.newton.events.data.mappers.EventMappers.toDomainEvent
import com.newton.events.data.mappers.EventMappers.toDomainEvents
import com.newton.events.data.mappers.EventMappers.toEventRegistration
import com.newton.events.data.mappers.EventMappers.toEventsEntity
import com.newton.events.data.mappers.EventMappers.toRegistrationResponse
import com.newton.events.data.mappers.EventMappers.toTicketEntity
import com.newton.events.data.paging.EventRemoteMediator
import com.newton.events.data.paging.PagingConstants.NETWORK_PAGE_SIZE
import com.newton.events.data.remote.EventApi
import com.newton.events.domain.models.Event
import com.newton.events.domain.models.EventRegistrationRequest
import com.newton.events.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val api: EventApi,
    private val db: AppDatabase,
    private val networkConfiguration: NetworkConfiguration,
    private val ticketDao: TicketDao,
    private val eventDao: EventDao
) : EventRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedEvents(): Flow<PagingData<EventsData>> {

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 1,
                enablePlaceholders = false,
                maxSize = NETWORK_PAGE_SIZE * 3,
                initialLoadSize = NETWORK_PAGE_SIZE
            ),
            remoteMediator = EventRemoteMediator(api, db, networkConfiguration),
            pagingSourceFactory = {
                db.eventDao.getPagedEvents()
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainEvent() }
        }
    }

    override suspend fun registerForEvent(eventId: Int, registrationRequest: EventRegistrationRequest): Flow<Resource<RegistrationResponse>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = api.registerForEvent(eventId = eventId, registrationRequest = registrationRequest)
            val registrationResponse = response.data.toEventRegistration()

            ticketDao.insertTicket(registrationResponse.toTicketEntity())

            ticketDao.getTicketByEventId(eventId)
                .collect { ticketEntity ->
                    if (ticketEntity != null) {
                        emit(Resource.Success(data = ticketEntity.toRegistrationResponse()))
                    } else {
                        emit(Resource.Error(message = "Failed to retrieve ticket from database"))
                    }
                }

        } catch (e: HttpException) {
            emit(Resource.Error(message = handleHttpError(e)))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Network error: Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "An unexpected error occurred: ${e.message}"))
        } finally {
            emit(Resource.Loading(false))
        }
    }

    override suspend fun searchEvents(eventName: String): Flow<Resource<List<EventsData>>> = flow {
        emit(Resource.Loading(true))
        try {

            val localEvents = eventDao.searchEvents("%${eventName}%")
            if (localEvents.isNotEmpty()) {
                emit(Resource.Success(data = localEvents.map { it.toDomainEvent() }))
            }
            val response = api.searchEvents(eventName)
            val data = response.data.toDomainEvents()
            eventDao.insertEvents(
                events = data.toEventsEntity()
            )

            emit(Resource.Success(data = data))
        } catch (e: HttpException) {
            emit(Resource.Error(message = handleHttpError(e)))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Network error: Please check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "An unexpected error occurred: ${e.message}"))
        } finally {
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getLatestEvents(count: Int): Flow<Resource<List<EventsData>>> {
        TODO("Not yet implemented")
    }


    override suspend fun getEventById(id: Int): Flow<Resource<Event>> = flow {

    }


    private fun handleHttpError(error: HttpException): String {
        return when (error.code()) {
            400 -> "Invalid request: Please check your input"
            401 -> "Unauthorized: Please log in again"
            403 -> "Access denied"
            404 -> "Resource not found"
            500, 502, 503 -> "Server error: Please try again later"
            else -> "Network error: ${error.message()}"
        }
    }

}

