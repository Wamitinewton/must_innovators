package com.newton.admin.presentation.events.states

import com.newton.core.domain.models.admin_models.Attendees
import com.newton.core.domain.models.admin_models.EventsData

data class RsvpsState(
    val isLoading:Boolean = false,
    val isSuccess:Boolean =false,
    val hasError:String? = null,
    val attendees: List<Attendees> = emptyList(),
    val selectedEvent: EventsData? = null,
)
