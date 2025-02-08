package com.newton.events.presentation.states

import com.newton.events.domain.models.Event

data class EventStates (
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String = ""
)