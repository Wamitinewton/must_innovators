package com.newton.events.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.domain.repositories.EventRepository
import com.newton.events.presentation.states.EventDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    val pagedEvents: Flow<PagingData<EventsData>> = eventRepository
        .getPagedEvents()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)

}


@HiltViewModel
class EventsSharedViewModel @Inject constructor(): ViewModel() {
    private val _selectedEvent = MutableStateFlow<EventsData?>(null)
    private val selectedEvent: StateFlow<EventsData?> = _selectedEvent.asStateFlow()

    private val _uiState = MutableStateFlow<EventDetailsState>(EventDetailsState.Initial)
    val uiState: StateFlow<EventDetailsState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            selectedEvent.collect { event ->
                _uiState.value = when (event) {
                    null -> EventDetailsState.Initial
                    else -> EventDetailsState.Success(event)
                }
            }
        }
    }

    fun setSelectedEvent(event: EventsData) {
        _selectedEvent.value = event
    }

    fun clearSelectedEvent() {
        _selectedEvent.value = null
    }
}