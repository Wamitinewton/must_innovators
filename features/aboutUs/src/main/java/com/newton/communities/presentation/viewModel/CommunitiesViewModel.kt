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
package com.newton.communities.presentation.viewModel

import androidx.lifecycle.*
import com.newton.communities.presentation.events.*
import com.newton.communities.presentation.state.*
import com.newton.network.*
import com.newton.network.domain.repositories.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class CommunitiesViewModel
@Inject
constructor(
    private val repository: CommunityRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<CommunitiesUiState>(CommunitiesUiState.Loading(isRefreshing = false))
    val uiState: StateFlow<CommunitiesUiState> = _uiState.asStateFlow()

    private val _uiEvents = MutableSharedFlow<CommunityUiEvent.Effect>()
    val uiEvents: SharedFlow<CommunityUiEvent.Effect> = _uiEvents.asSharedFlow()

    init {
        loadCommunities()
    }

    fun onEvent(event: CommunityUiEvent.Action) {
        when (event) {
            is CommunityUiEvent.Action.RefreshCommunities -> refreshCommunities()
            is CommunityUiEvent.Action.DismissError -> dismissError()
        }
    }

    private fun refreshCommunities() {
        loadCommunities(forceRefresh = true)
    }

    private fun dismissError() {
        val currentState = _uiState.value
        if (currentState is CommunitiesUiState.Error) {
            _uiState.value =
                CommunitiesUiState.Content(
                    communities = currentState.communities,
                    isRefreshing = false
                )
        }
    }

    private fun loadCommunities(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            if (!forceRefresh) {
                _uiState.value = CommunitiesUiState.Loading(isRefreshing = false)
            } else {
                when (val currentState = _uiState.value) {
                    is CommunitiesUiState.Content -> {
                        _uiState.value =
                            CommunitiesUiState.Content(
                                communities = currentState.communities,
                                isRefreshing = true
                            )
                    }

                    else -> {
                        _uiState.value = CommunitiesUiState.Loading(isRefreshing = true)
                    }
                }
            }

            repository.getCommunities(isRefreshing = forceRefresh).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        val errorMessage = result.message ?: "An unexpected error occurred"
                        val currentCommunities =
                            when (val currentState = _uiState.value) {
                                is CommunitiesUiState.Content -> currentState.communities
                                is CommunitiesUiState.Error -> currentState.communities
                                else -> emptyList()
                            }

                        _uiState.value =
                            CommunitiesUiState.Error(
                                message = errorMessage,
                                communities = currentCommunities
                            )

                        _uiEvents.emit(CommunityUiEvent.Effect.ShowSnackbar(errorMessage))
                    }

                    is Resource.Loading -> {
                        if (_uiState.value is CommunitiesUiState.Loading) {
                            _uiState.value =
                                CommunitiesUiState.Loading(
                                    isRefreshing = forceRefresh
                                )
                        }
                    }

                    is Resource.Success -> {
                        _uiState.value =
                            CommunitiesUiState.Content(
                                communities = result.data ?: emptyList(),
                                isRefreshing = false
                            )

                        if (forceRefresh) {
                            _uiEvents.emit(CommunityUiEvent.Effect.ShowSnackbar("Communities loaded successfully"))
                        }
                    }
                }
            }
        }
    }
}
