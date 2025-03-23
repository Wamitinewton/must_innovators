package com.newton.events.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.data.response.admin.RegistrationResponse
import com.newton.core.domain.repositories.EventRepository
import com.newton.core.enums.EventRegistrationFlow
import com.newton.core.utils.Resource
import com.newton.events.presentation.events.EventRsvpUiEvent
import com.newton.events.presentation.states.EventRegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EventRsvpViewmodel @Inject constructor(
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
                                ticketStateBus.updateTicket(result.data)
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
class TicketStateBus @Inject constructor() {
    private val _ticketState = MutableStateFlow<RegistrationResponse?>(null)
    val ticketState: StateFlow<RegistrationResponse?> = _ticketState

    fun updateTicket(ticket: RegistrationResponse?) {
        _ticketState.value = ticket
    }
}

class RsvpSharedViewModel @Inject constructor(
    ticketStateBus: TicketStateBus
) : ViewModel() {
    var eventTicket: StateFlow<RegistrationResponse?> =
        ticketStateBus.ticketState.stateIn(viewModelScope, SharingStarted.Lazily, null)


}


sealed class NavigationEvent {
    data object NavigateToTicket : NavigationEvent()
}