/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.admin.presentation.events.viewmodel

import androidx.lifecycle.*
import com.newton.admin.presentation.events.events.*
import com.newton.admin.presentation.events.states.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class EventsViewModel
@Inject
constructor(
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
            is EventEvents.GetEventFeedbacks -> getEventsFeedbacks(event.eventId, event.isRefresh)
            EventEvents.LoadEvents -> loadEvents()
        }
    }

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            repository.getListOfEvents().collectLatest { result ->
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

    private fun getEventsFeedbacks(
        eventId: Int,
        isRefresh: Boolean
    ) {
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
