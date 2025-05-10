/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.events.data.repository

import androidx.paging.*
import com.newton.database.dao.*
import com.newton.database.db.*
import com.newton.database.mappers.*
import com.newton.events.data.paging.*
import com.newton.events.data.paging.PagingConstants.NETWORK_PAGE_SIZE
import com.newton.network.*
import com.newton.network.data.mappers.*
import com.newton.network.data.remote.*
import com.newton.network.data.dto.admin.*
import com.newton.network.domain.models.adminModels.*
import com.newton.network.domain.models.eventModels.*
import com.newton.network.domain.repositories.*
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
        safeApiCall {
            val response =
                api.registerForEvent(
                    eventId = eventId,
                    registrationRequest = registrationRequest
                )
            val registrationResponse = response.data.toEventRegistration()

            ticketDao.insertTicket(registrationResponse.toTicketEntity())

            registrationResponse
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
