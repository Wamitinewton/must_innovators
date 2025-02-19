package com.newton.events.presentation.states

import com.newton.core.domain.models.event_models.EventsData

sealed class EventDetailsState {
    data object Initial : EventDetailsState()
    data class Success(val event: EventsData) : EventDetailsState()
    data class Error(val message: String) : EventDetailsState()
}