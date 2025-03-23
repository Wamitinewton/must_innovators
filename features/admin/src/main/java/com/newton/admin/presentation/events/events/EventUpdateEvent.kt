package com.newton.admin.presentation.events.events

import com.newton.core.domain.models.admin_models.EventsData

sealed class EventUpdateEvent {
    data class Update(val eventId:Int):EventUpdateEvent()
    data class NameChanged(val name:String):EventUpdateEvent()
    data class CategoryChanged(val category:String):EventUpdateEvent()
    data class DescriptionChanged(val description:String):EventUpdateEvent()
    data class LocationChanged(val location:String):EventUpdateEvent()
    data class OrganizerChanged(val organizer:String):EventUpdateEvent()
    data class ContactEmailChanged(val email:String):EventUpdateEvent()
    data class VirtualChanged(val virtual:Boolean):EventUpdateEvent()
}

sealed class UpdateEvent {
    data object Initial : UpdateEvent()
    data class Success(val event: EventsData) : UpdateEvent()
    data class Error(val message: String) : UpdateEvent()
}