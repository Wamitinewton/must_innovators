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
package com.newton.home.presentation.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.newton.core.domain.models.homeModels.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import com.newton.home.presentation.states.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class PartnersViewModel
@Inject
constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val _partnersState = MutableStateFlow<PartnersUiState>(PartnersUiState.Loading)
    val partnersState: StateFlow<PartnersUiState> = _partnersState.asStateFlow()

    init {
        loadPartners()
    }

    private fun loadPartners() {
        viewModelScope.launch {
            _partnersState.value = PartnersUiState.Loading

            homeRepository.getPartners().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        if (result.isLoading) {
                            _partnersState.value = PartnersUiState.Loading
                        }
                    }

                    is Resource.Success -> {
                        val partners = result.data ?: emptyList()
                        _partnersState.value =
                            if (partners.isEmpty()) {
                                PartnersUiState.Empty
                            } else {
                                PartnersUiState.Success(partners)
                            }
                    }

                    is Resource.Error -> {
                        _partnersState.value =
                            PartnersUiState.Error(
                                result.message ?: "Something went wrong"
                            )
                    }
                }
            }
        }
    }

    fun refreshPartners() {
        loadPartners()
    }
}

@HiltViewModel
class PartnersSharedViewModel
@Inject
constructor() : ViewModel() {
    var selectedPartner by mutableStateOf<PartnersData?>(null)
        private set

    fun updateSelectedPartner(partnersData: PartnersData) {
        selectedPartner = partnersData
    }

    fun clearSelectedEvent() {
        selectedPartner = null
    }
}
