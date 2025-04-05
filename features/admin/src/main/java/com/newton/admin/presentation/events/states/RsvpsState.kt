package com.newton.admin.presentation.events.states

import com.newton.core.domain.models.adminModels.*

data class RsvpsState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val hasError: String? = null,
    val attendees: List<Attendee> = emptyList(),
    val selectedEvent: EventsData? = null
)
