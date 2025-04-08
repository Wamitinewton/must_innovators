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
class ClubBioViewModel
@Inject
constructor(
    private val clubBioRepository: ClubBioRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ClubBioUiState>(ClubBioUiState.Loading)
    val uiState: StateFlow<ClubBioUiState> = _uiState.asStateFlow()

    init {
        fetchClubBio()
    }

    private fun fetchClubBio() {
        viewModelScope.launch {
            try {
                clubBioRepository.getClubBio().onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            _uiState.value =
                                ClubBioUiState.Error(result.message ?: "An unknown error occurred")
                        }

                        is Resource.Loading -> {
                            if (result.isLoading) {
                                _uiState.value = ClubBioUiState.Loading
                            }
                        }

                        is Resource.Success -> {
                            result.data?.let { data ->
                                _uiState.value = ClubBioUiState.Success(data)
                            } ?: run {
                                _uiState.value = ClubBioUiState.Error("Data is null")
                            }
                        }
                    }
                }.launchIn(this)
            } catch (e: Exception) {
                _uiState.value = ClubBioUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun retry() {
        fetchClubBio()
    }
}
