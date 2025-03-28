package com.newton.events.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.domain.repositories.EventRepository
import com.newton.core.utils.Resource
import com.newton.events.presentation.states.UserTicketsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserTicketsViewModel @Inject constructor (
    private val eventRepository: EventRepository
): ViewModel() {

    private val _userTicketsState = MutableStateFlow<UserTicketsUiState>(UserTicketsUiState.Initial)
    val userTicketsUiState: StateFlow<UserTicketsUiState> = _userTicketsState.asStateFlow()

    init {
        loadUserTickets()
    }

    private fun loadUserTickets() {
        viewModelScope.launch {
            eventRepository.getUserTickets()
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