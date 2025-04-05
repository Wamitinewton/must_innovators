package com.newton.admin.presentation.events.viewmodel

import androidx.lifecycle.*
import com.newton.admin.presentation.events.events.*
import com.newton.core.domain.models.adminModels.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class AdminEventsSharedViewModel : ViewModel() {
    private val _selectedEvent = MutableStateFlow<EventsData?>(null)
    private val selectedEvent: StateFlow<EventsData?> = _selectedEvent.asStateFlow()

    private val _uiState = MutableStateFlow<UpdateEvent>(UpdateEvent.Initial)
    val uiState: StateFlow<UpdateEvent> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            selectedEvent.collect { event ->
                _uiState.value =
                    when (event) {
                        null -> UpdateEvent.Initial
                        else -> UpdateEvent.Success(event)
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
