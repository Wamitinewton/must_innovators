package com.newton.events.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.utils.Resource
import com.newton.events.domain.models.Event
import com.newton.events.domain.usecases.EventUseCase
import com.newton.events.presentation.states.EventStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventUseCases: EventUseCase
) : ViewModel() {
    private val _eventState = MutableStateFlow(EventStates())
    val eventState: StateFlow<EventStates> = _eventState

    private val seenEvents = mutableListOf<Event>()

    init {
        viewModelScope.launch {
            initEvents()
        }
    }
    private suspend fun initEvents() {
        delay(1000)
        viewModelScope.launch {
            _eventState.update { it.copy(isLoading = true) }
            delay(1000)
            val initialEvents = eventUseCases.getAllEvents()
            initialEvents.collect{eventList->
                handleEventResponse(eventList)
                eventList.data?.let {events->
                        seenEvents.addAll(events)
                }
            }
        }
    }
    fun loadMoreEvents() {
        viewModelScope.launch {
            if (_eventState.value.isLoading) return@launch
            _eventState.update { it.copy(isLoading = true) }
            delay(2000)
             eventUseCases.refreshEvents().collect{eventList->
                 handleEventResponse(eventList)
                 eventList.data?.let {events->
                     seenEvents.addAll(events)
                 }
             }
        }
    }
    private fun handleEventResponse(event: Resource<List<Event>>) {
        _eventState.update { state ->
            when (event) {
                is Resource.Success -> state.copy(
                    isLoading = false,
                    events = event.data?: emptyList(),
                )

                is Resource.Error -> state.copy(
                    isLoading = false,
                    errorMessage = event.message ?: "An unexpected error occurred",
                )

                is Resource.Loading -> state.copy(isLoading = event.isLoading)
            }
        }
    }
}