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
import com.newton.events.presentation.states.*
import com.newton.network.*
import com.newton.network.domain.repositories.*
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
