package com.newton.admin.presentation.events.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.admin.presentation.events.events.UpdateEvent
import com.newton.core.domain.models.admin_models.EventsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminEventsSharedViewModel : ViewModel(){
    private val _selectedEvent = MutableStateFlow<EventsData?>(null)
    private val selectedEvent: StateFlow<EventsData?> = _selectedEvent.asStateFlow()


    private val _uiState = MutableStateFlow<UpdateEvent>(UpdateEvent.Initial)
    val uiState: StateFlow<UpdateEvent> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            selectedEvent.collect { event ->
                _uiState.value = when (event) {
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