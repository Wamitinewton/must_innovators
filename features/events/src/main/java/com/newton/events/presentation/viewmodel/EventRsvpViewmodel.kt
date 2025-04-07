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
package com.newton.events.presentation.viewmodel

import androidx.lifecycle.*
import com.newton.core.enums.*
import com.newton.events.presentation.events.*
import com.newton.events.presentation.states.*
import com.newton.network.*
import com.newton.network.data.response.admin.*
import com.newton.network.domain.repositories.*
import dagger.hilt.android.lifecycle.*
import dagger.hilt.android.scopes.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class EventRsvpViewmodel
@Inject
constructor(
    private val repository: EventRepository,
    private val stateHolder: EventRegistrationStateHolder,
    private val ticketStateBus: TicketStateBus
) : ViewModel() {
    val eventRegistrationState: StateFlow<EventRegistrationState> = stateHolder.registrationState

    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    fun onEvent(event: EventRsvpUiEvent) {
        stateHolder.updateState(event)

        when (event) {
            is EventRsvpUiEvent.SubmitRegistration -> {
                registerForEvent(event.eventId)
            }

            else -> {}
        }
    }

    private fun registerForEvent(eventId: Int) {
        if (!stateHolder.isFormValid()) {
            return
        }
        viewModelScope.launch {
            try {
                stateHolder.setLoading(true)
                repository.registerForEvent(eventId, stateHolder.getEventRegistrationRequest())
                    .onEach { result ->
                        when (result) {
                            is Resource.Error -> {
                                stateHolder.setError(result.message)
                            }

                            is Resource.Loading -> {
                                stateHolder.setLoading(result.isLoading)
                            }

                            is Resource.Success -> {
                                stateHolder.setSuccess(
                                    result.data,
                                    EventRegistrationFlow.REGISTRATION_SUCCESS
                                )
//                                ticketStateBus.updateTicket(result.data)
                                _navigationEvents.send(NavigationEvent.NavigateToTicket)
                            }
                        }
                    }
                    .catch { e ->
                        stateHolder.setLoading(false)
                        stateHolder.setError(e.message)
                    }
                    .launchIn(this)
            } catch (e: Exception) {
                stateHolder.setLoading(false)
                stateHolder.setError(e.message)
            }
        }
    }
}

@ActivityRetainedScoped
class TicketStateBus
@Inject
constructor() {
    private val _ticketState = MutableStateFlow<RegistrationResponse?>(null)
    val ticketState: StateFlow<RegistrationResponse?> = _ticketState

    fun updateTicket(ticket: RegistrationResponse?) {
        _ticketState.value = ticket
    }
}

class RsvpSharedViewModel
@Inject
constructor(
    ticketStateBus: TicketStateBus
) : ViewModel() {
    var eventTicket: StateFlow<RegistrationResponse?> =
        ticketStateBus.ticketState.stateIn(viewModelScope, SharingStarted.Lazily, null)
}

sealed class NavigationEvent {
    data object NavigateToTicket : NavigationEvent()
}
