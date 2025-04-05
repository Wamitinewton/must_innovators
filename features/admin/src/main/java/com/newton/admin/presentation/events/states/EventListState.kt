package com.newton.admin.presentation.events.states

import com.newton.core.domain.models.adminModels.*

data class EventListState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val hasError: String? = null,
    val events: List<EventsData> = emptyList(),
    val selectedEvent: EventsData? = null,
    val isEditing: Boolean = false
)
