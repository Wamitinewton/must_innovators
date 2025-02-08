package com.newton.events.domain.repository

import com.newton.core.utils.Resource
import com.newton.events.domain.models.Event
import com.newton.events.domain.models.EventRequest
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun refreshEvents(): Flow<Resource<List<Event>>>
    suspend fun getEventById(id: Int): Flow<Resource<Event>>
    fun getAllEvents(): Flow<Resource<List<Event>>>
    suspend fun updateEvent(id: Int,request: EventRequest): Flow<Resource<Event>>
    suspend fun deleteEvent(id: Int) : Flow<Resource<String>>
    suspend fun createEvent(request: EventRequest): Flow<Resource<Event>>
}