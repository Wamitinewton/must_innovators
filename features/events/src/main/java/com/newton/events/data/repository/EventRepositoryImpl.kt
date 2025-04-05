package com.newton.events.data.repository

import androidx.paging.*
import com.newton.core.data.mappers.*
import com.newton.core.data.remote.*
import com.newton.core.data.response.admin.*
import com.newton.core.domain.models.adminModels.*
import com.newton.core.domain.models.eventModels.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import com.newton.database.dao.*
import com.newton.database.db.*
import com.newton.database.mappers.*
import com.newton.events.data.paging.*
import com.newton.events.data.paging.PagingConstants.NETWORK_PAGE_SIZE
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class EventRepositoryImpl
@Inject
constructor(
    private val api: EventService,
    private val db: AppDatabase,
    private val ticketDao: TicketDao,
    private val eventDao: EventDao
) : EventRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedEvents(): Flow<PagingData<EventsData>> {
        return Pager(
            config =
            PagingConfig(
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
    ): Flow<Resource<RegistrationResponse>> =
        flow {
            emitAll(
                safeApiCall {
                    val response =
                        api.registerForEvent(
                            eventId = eventId,
                            registrationRequest = registrationRequest
                        )
                    val registrationResponse = response.data.toEventRegistration()

                    ticketDao.insertTicket(registrationResponse.toTicketEntity())

                    ticketDao.getTicketByEventId(eventId).first()?.toRegistrationResponse()
                        ?: throw Exception("Failed to retrieve ticket from database")
                }
            )
        }

    override suspend fun searchEvents(eventName: String): Flow<Resource<List<EventsData>>> =
        flow {
            emitAll(
                safeApiCall {
                    val localEvents = eventDao.searchEvents("%$eventName%")

                    if (localEvents.isNotEmpty()) {
                        return@safeApiCall localEvents.map { it.toDomainEvent() }
                    }
                    val response = api.searchEvents(eventName)
                    val data = response.data.toDomainEvents()
                    eventDao.insertEvents(events = data.toEventsEntity())
                    data
                }
            )
        }

    override suspend fun getLatestEvents(count: Int): Flow<Resource<List<EventsData>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserTickets(): Flow<Resource<List<RegistrationResponse>>> =
        flow {
            emit(Resource.Loading())

            val localTickets =
                ticketDao.getAllTickets().firstOrNull()?.map { it.toRegistrationResponse() }
            if (!localTickets.isNullOrEmpty()) {
                emit(Resource.Success(localTickets))
                return@flow
            }
            safeApiCall {
                val response = api.getUserTickets()
                val tickets = response.data.map { it.toEventRegistration() }

                ticketDao.insertTickets(tickets.map { it.toTicketEntity() })

                tickets
            }.collect { emit(it) }
        }.flowOn(Dispatchers.IO)

    override suspend fun getEventById(id: Int): Flow<Resource<Event>> =
        flow {
        }
}
