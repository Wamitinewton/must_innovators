package com.newton.events.presentation.states

import com.newton.core.domain.models.event_models.EventsData

sealed class EventStates {
    data object Initial : EventStates()
    data class Success(val event: EventsData) : EventStates()
    data class Error(val message: String) : EventStates()
}