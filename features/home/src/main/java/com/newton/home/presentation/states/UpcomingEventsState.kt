package com.newton.home.presentation.states

import com.newton.events.domain.models.Event

data class UpcomingEventsState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val upcomingEvents: List<Event> = emptyList(),
)
