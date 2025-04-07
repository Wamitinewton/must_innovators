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
import com.newton.communities.presentation.event.*
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
        MutableStateFlow<CommunitiesUiState>(CommunitiesUiState.Loading)
    val uiState: StateFlow<CommunitiesUiState> = _uiState.asStateFlow()

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
        loadCommunities()
    }

    private fun dismissError() {
        val currentState = _uiState.value
        if (currentState is CommunitiesUiState.Error) {
            _uiState.value = CommunitiesUiState.Initial
        }
    }

    private fun loadCommunities() {
        viewModelScope.launch {
            repository.getCommunities().onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        val errorMessage = result.message ?: "An unexpected error occurred"
                        _uiState.value =
                            CommunitiesUiState.Error(
                                message = errorMessage
                            )
                    }

                    is Resource.Loading -> {
                        if (result.isLoading) {
                            _uiState.value =
                                CommunitiesUiState.Loading
                        }
                    }

                    is Resource.Success -> {
                        _uiState.value =
                            CommunitiesUiState.Success(
                                communities = result.data ?: emptyList()
                            )
                    }
                }
            }.launchIn(this)
        }
    }
}
