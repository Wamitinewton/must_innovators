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
package com.newton.admin.presentation.community.viewmodels

import androidx.lifecycle.*
import com.newton.admin.presentation.community.events.*
import com.newton.network.domain.models.aboutUs.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CommunitySharedViewModel : ViewModel() {
    private val _selectedCommunity = MutableStateFlow<Community?>(null)
    private val selectedCommunity: StateFlow<Community?> = _selectedCommunity.asStateFlow()

    private val _uiState = MutableStateFlow<UpdateCommunityEffect>(UpdateCommunityEffect.Initial)
    val uiState: StateFlow<UpdateCommunityEffect> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            selectedCommunity.collect { community ->
                _uiState.value =
                    when (community) {
                        null -> UpdateCommunityEffect.Initial
                        else -> UpdateCommunityEffect.Success(community)
                    }
            }
        }
    }

    fun setSelectedCommunity(event: Community) {
        _selectedCommunity.value = event
    }

    fun clearSelectedCommunity() {
        _selectedCommunity.value = null
    }
}
