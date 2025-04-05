package com.newton.admin.presentation.community.viewmodels

import androidx.lifecycle.*
import com.newton.admin.presentation.community.events.*
import com.newton.core.domain.models.aboutUs.*
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
