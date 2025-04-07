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
import com.newton.communities.presentation.state.*
import com.newton.network.*
import com.newton.network.domain.repositories.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class ExecutiveViewModel
@Inject
constructor(
    private val repository: ExecutiveRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ExecutiveUiState>(ExecutiveUiState.Loading)
    val uiState: StateFlow<ExecutiveUiState> = _uiState.asStateFlow()

    init {
        loadExecutives()
    }

    private fun loadExecutives() {
        viewModelScope.launch {
            _uiState.value = ExecutiveUiState.Loading

            repository.getExecutives().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.value =
                            ExecutiveUiState.Success(
                                executives = result.data ?: emptyList()
                            )
                    }

                    is Resource.Error -> {
                        _uiState.value =
                            ExecutiveUiState.Error(
                                message = result.message ?: "Unknown error occurred"
                            )
                    }

                    is Resource.Loading -> {
                        if (result.isLoading) {
                            _uiState.value = ExecutiveUiState.Loading
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    fun retryLoadExecutives() {
        loadExecutives()
    }

    fun clearError() {
        if (_uiState.value is ExecutiveUiState.Error) {
            loadExecutives()
        }
    }
}
