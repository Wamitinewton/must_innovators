package com.newton.events.presentation.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.newton.core.domain.models.adminModels.*
import com.newton.core.domain.repositories.*
import com.newton.events.presentation.states.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class EventViewModel
@Inject
constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
    val pagedEvents: Flow<PagingData<EventsData>> =
        eventRepository
            .getPagedEvents()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
}

@HiltViewModel
class EventsSharedViewModel
@Inject
constructor() : ViewModel() {
    private val _selectedEvent = MutableStateFlow<EventsData?>(null)
    private val selectedEvent: StateFlow<EventsData?> = _selectedEvent.asStateFlow()

    private val _uiState = MutableStateFlow<EventDetailsState>(EventDetailsState.Initial)
    val uiState: StateFlow<EventDetailsState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            selectedEvent.collect { event ->
                _uiState.value =
                    when (event) {
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
