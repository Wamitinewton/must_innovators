package com.newton.admin.presentation.events.events

import com.newton.core.domain.models.admin_models.EventsData

sealed class EventEvents {
    data class SelectedEvent(val event: EventsData?):EventEvents()
    data class EditingEvent(val editing: Boolean):EventEvents()
    data class GetEventsAttendees(val eventId: Int):EventEvents()
    data class GetEventFeedbacks(val eventId: Int,val isRefresh:Boolean):EventEvents()
    data object LoadEvents:EventEvents()
}