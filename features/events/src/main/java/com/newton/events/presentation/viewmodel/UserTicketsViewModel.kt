package com.newton.events.presentation.viewmodel

import androidx.lifecycle.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import com.newton.events.presentation.states.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class UserTicketsViewModel
@Inject
constructor(
    private val eventRepository: EventRepository
) : ViewModel() {
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
                            _userTicketsState.value =
                                UserTicketsUiState.Error(result.message ?: "An error occurred")
                        }

                        is Resource.Loading -> {
                            if (result.isLoading) {
                                _userTicketsState.value = UserTicketsUiState.Loading
                            }
                        }

                        is Resource.Success -> {
                            _userTicketsState.value =
                                UserTicketsUiState.Success(result.data ?: emptyList())
                        }
                    }
                }.launchIn(this)
        }
    }
}
