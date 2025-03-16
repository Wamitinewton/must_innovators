package com.newton.admin.presentation.events.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.newton.admin.presentation.events.events.EventEvents
import com.newton.admin.presentation.events.states.AddEventEffect
import com.newton.admin.presentation.events.states.AdminFeedbackState
import com.newton.admin.presentation.events.states.EventListState
import com.newton.admin.presentation.events.states.AddEventState
import com.newton.admin.presentation.events.states.RsvpsState
import com.newton.common_ui.ui.toLocaltime
import com.newton.core.domain.models.admin_models.AddEventRequest
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.domain.repositories.AdminRepository
import com.newton.core.domain.repositories.EventRepository
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val repository: AdminRepository,
    private val eventsRepository: EventRepository
) : ViewModel() {
    private val _eventListState = MutableStateFlow(EventListState())
    val eventList: StateFlow<EventListState> = _eventListState.asStateFlow()

    private val _rsvps = MutableStateFlow(RsvpsState())
    val rsvpState: StateFlow<RsvpsState> = _rsvps.asStateFlow()

    private val _feedbacksState = MutableStateFlow(AdminFeedbackState())
    val feedbacksState: StateFlow<AdminFeedbackState> = _feedbacksState.asStateFlow()
    fun handleEvent(event: EventEvents) {
        when (event) {
            is EventEvents.SelectedEvent -> _eventListState.update { it.copy(selectedEvent = event.event) }
            is EventEvents.EditingEvent -> _eventListState.update { it.copy(isEditing = event.editing) }
            is EventEvents.GetEventsAttendees -> getEventsAttendees(event.eventId)
            is EventEvents.GetEventFeedbacks -> getEventsFeedbacks(event.eventId,event.isRefresh)
        }
    }


    init {
        loadEvents()
    }

    private fun loadEvents(){
        viewModelScope.launch {
            repository.getListOfEvents().collectLatest { result->
                when (result) {
                    is Resource.Error -> {
                        _eventListState.update { it.copy(hasError = result.message) }
                    }

                    is Resource.Loading -> {
                        _eventListState.update { it.copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        result.data?.let { events ->
                            _eventListState.update {
                                it.copy(
                                    isSuccess = true,
                                    isLoading = false,
                                    hasError = null,
                                    events = events
                                )
                            }
                        }

                    }
                }
            }
        }
    }
    
    private fun getEventsAttendees(eventId: Int) {
        viewModelScope.launch {
            repository.getRegistrationList(eventId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _rsvps.update { it.copy(hasError = result.message) }
                    }

                    is Resource.Loading -> {
                        _rsvps.update { it.copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        result.data?.let { attendee ->
                            _rsvps.update {
                                it.copy(
                                    isSuccess = true,
                                    isLoading = false,
                                    hasError = null,
                                    attendees = attendee
                                )
                            }
                        }

                    }
                }
            }
        }
    }

    private fun getEventsFeedbacks(eventId: Int,isRefresh:Boolean) {
        viewModelScope.launch {
            repository.getEventFeedbackBYId(eventId, isRefresh).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _feedbacksState.update { it.copy(hasError = result.message) }
                    }

                    is Resource.Loading -> {
                        _feedbacksState.update { it.copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        result.data?.let { events ->
                            _feedbacksState.update {
                                it.copy(
                                    isSuccess = true,
                                    isLoading = false,
                                    hasError = null,
                                    feedbacks = events
                                )
                            }
                        }

                    }
                }
            }
        }
    }

}