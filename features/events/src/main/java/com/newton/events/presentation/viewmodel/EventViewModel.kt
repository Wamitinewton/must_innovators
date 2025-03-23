package com.newton.events.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.domain.repositories.EventRepository
import com.newton.core.utils.Resource
import com.newton.events.presentation.events.UserTicketsEvent
import com.newton.events.presentation.states.EventDetailsState
import com.newton.events.presentation.states.UserTicketsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _userTicketsState = MutableStateFlow<UserTicketsUiState>(UserTicketsUiState.Initial)
    val userTicketsUiState: StateFlow<UserTicketsUiState> = _userTicketsState.asStateFlow()

    private var currentEmail: String? = null

    val pagedEvents: Flow<PagingData<EventsData>> = eventRepository
        .getPagedEvents()
        .distinctUntilChanged()
        .cachedIn(viewModelScope)

    fun onEvent(event: UserTicketsEvent) {
        when (event) {
            UserTicketsEvent.ClearErrors -> {
                if (_userTicketsState.value is UserTicketsUiState.Error) {
                    _userTicketsState.value = UserTicketsUiState.Initial
                }
            }
            is UserTicketsEvent.Initialize -> loadUserTickets(event.email)
            is UserTicketsEvent.Refresh -> loadUserTickets(event.email)
        }
    }

    private fun loadUserTickets(email: String) {
        currentEmail = email
        viewModelScope.launch {
            eventRepository.getUserTickets(email)
                .onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            _userTicketsState.value = UserTicketsUiState.Error(result.message ?: "An error occurred")
                        }
                        is Resource.Loading -> {
                            if (result.isLoading) {
                                _userTicketsState.value = UserTicketsUiState.Loading
                            }
                        }
                        is Resource.Success -> {
                            _userTicketsState.value = UserTicketsUiState.Success(result.data ?: emptyList())
                        }
                    }
                }.launchIn(this)
        }
    }
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