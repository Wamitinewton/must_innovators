package com.newton.events.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.newton.core.data.mappers.toDomainEvents
import com.newton.core.data.mappers.toEventRegistration
import com.newton.core.data.remote.EventService
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.data.response.admin.RegistrationResponse
import com.newton.core.domain.models.event_models.Event
import com.newton.core.domain.models.event_models.EventRegistrationRequest
import com.newton.core.domain.repositories.EventRepository
import com.newton.core.utils.Resource
import com.newton.core.utils.safeApiCall
import com.newton.database.dao.EventDao
import com.newton.database.dao.TicketDao
import com.newton.database.db.AppDatabase
import com.newton.database.mappers.toDomainEvent
import com.newton.database.mappers.toEventsEntity
import com.newton.database.mappers.toRegistrationResponse
import com.newton.database.mappers.toTicketEntity
import com.newton.events.data.paging.EventRemoteMediator
import com.newton.events.data.paging.PagingConstants.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val api: EventService,
    private val db: AppDatabase,
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
                maxSize = NETWORK_PAGE_SIZE * 5,
                initialLoadSize = NETWORK_PAGE_SIZE * 2,
                jumpThreshold = NETWORK_PAGE_SIZE * 4
            ),
            remoteMediator = EventRemoteMediator(api, db, eventDao),
            pagingSourceFactory = {
                db.eventDao.getPagedEvents()
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainEvent() }
        }
    }

    override suspend fun registerForEvent(
        eventId: Int,
        registrationRequest: EventRegistrationRequest
    ): Flow<Resource<RegistrationResponse>> = flow {
        emitAll(safeApiCall {
            val response =
                api.registerForEvent(eventId = eventId, registrationRequest = registrationRequest)
            val registrationResponse = response.data.toEventRegistration()

            ticketDao.insertTicket(registrationResponse.toTicketEntity())

            ticketDao.getTicketByEventId(eventId).first()?.toRegistrationResponse()
                ?: throw Exception("Failed to retrieve ticket from database")
        })
    }

    override suspend fun searchEvents(eventName: String): Flow<Resource<List<EventsData>>> = flow {
        emitAll(safeApiCall {
            val localEvents = eventDao.searchEvents("%${eventName}%")

            if (localEvents.isNotEmpty()) {
                return@safeApiCall localEvents.map { it.toDomainEvent() }
            }
            val response = api.searchEvents(eventName)
            val data = response.data.toDomainEvents()
            eventDao.insertEvents(events = data.toEventsEntity())
            data
        })
    }

    override suspend fun getLatestEvents(count: Int): Flow<Resource<List<EventsData>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserTickets(email: String): Flow<Resource<List<RegistrationResponse>>> =
        flow {
            emitAll(safeApiCall {
                val localTickets = ticketDao.getAllTickets()
                if (localTickets.isNotEmpty()) {
                    return@safeApiCall localTickets.map { it.toRegistrationResponse() }
                }
                val response = api.getUserTickets(email)
                val tickets = response.data.map { it.toEventRegistration() }
                ticketDao.insertTickets(tickets.map { it.toTicketEntity() })
                tickets
            })
        }


    override suspend fun getEventById(id: Int): Flow<Resource<Event>> = flow {

    }


}

