package com.newton.events.presentation.states

import androidx.paging.PagingData
import com.newton.core.domain.models.event_models.EventsData
import kotlinx.coroutines.flow.Flow

sealed class EventStates {
    data object Initial : EventStates()
    data object Loading : EventStates()
    data class Success(val events: Flow<PagingData<EventsData>>) : EventStates()
    data class Error(val message: String) : EventStates()
}