package com.newton.events.domain.usecases

import com.newton.events.domain.models.EventRequest
import com.newton.events.domain.repository.EventRepository
import javax.inject.Inject

class EventUseCase @Inject constructor(
    private val eventRepository: EventRepository
){
    suspend fun getEventById(id:Int)= eventRepository.getEventById(id)
    fun getAllEvents() = eventRepository.getAllEvents()
    fun refreshEvents() = eventRepository.refreshEvents()
    suspend fun updateEvent(id: Int,request: EventRequest)= eventRepository.updateEvent(id, request)
    suspend fun deleteEvent(id: Int)=eventRepository.deleteEvent(id)
    suspend fun createEvent(request: EventRequest)=eventRepository.createEvent(request)
}
