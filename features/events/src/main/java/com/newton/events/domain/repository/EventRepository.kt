package com.newton.events.domain.repository

import androidx.paging.PagingData
import com.newton.core.domain.models.event_models.EventsData
import com.newton.core.domain.models.event_models.RegistrationResponse
import com.newton.core.utils.Resource
import com.newton.database.entities.TicketsEntity
import com.newton.events.domain.models.Event
import com.newton.events.domain.models.EventRegistrationRequest
import com.newton.events.domain.models.EventRequest
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getPagedEvents(): Flow<PagingData<EventsData>>
    suspend fun getEventById(id: Int): Flow<Resource<Event>>
    suspend fun registerForEvent(eventId: Int ,registrationRequest: EventRegistrationRequest): Flow<Resource<RegistrationResponse>>
    suspend fun searchEvents(eventName: String): Flow<Resource<List<EventsData>>>
    suspend fun getLatestEvents(count: Int): Flow<Resource<List<EventsData>>>
}