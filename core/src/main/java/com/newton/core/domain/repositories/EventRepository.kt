package com.newton.core.domain.repositories

import androidx.paging.*
import com.newton.core.data.response.admin.*
import com.newton.core.domain.models.adminModels.*
import com.newton.core.domain.models.eventModels.*
import com.newton.core.utils.*
import kotlinx.coroutines.flow.*

interface EventRepository {
    fun getPagedEvents(): Flow<PagingData<EventsData>>

    suspend fun getEventById(id: Int): Flow<Resource<Event>>

    suspend fun registerForEvent(
        eventId: Int,
        registrationRequest: EventRegistrationRequest
    ): Flow<Resource<RegistrationResponse>>

    suspend fun searchEvents(eventName: String): Flow<Resource<List<EventsData>>>

    suspend fun getLatestEvents(count: Int): Flow<Resource<List<EventsData>>>

    suspend fun getUserTickets(): Flow<Resource<List<RegistrationResponse>>>
}
