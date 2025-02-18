package com.newton.events.domain.repository

import androidx.paging.PagingData
import com.newton.core.domain.models.event_models.EventsData
import com.newton.core.domain.models.event_models.RegistrationResponse
import com.newton.core.utils.Resource
import com.newton.events.domain.models.Event
import com.newton.events.domain.models.EventRegistrationRequest
import com.newton.events.domain.models.EventRequest
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getPagedEvents(): Flow<PagingData<EventsData>>
    suspend fun getEventById(id: Int): Flow<Resource<Event>>
    suspend fun updateEvent(id: Int,request: EventRequest): Flow<Resource<Event>>
    suspend fun deleteEvent(id: Int) : Flow<Resource<String>>
    suspend fun createEvent(request: EventRequest): Flow<Resource<Event>>
    suspend fun registerForEvent(eventId: Int ,registrationRequest: EventRegistrationRequest): Flow<Resource<RegistrationResponse>>
}