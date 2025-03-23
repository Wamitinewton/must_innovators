package com.newton.admin.presentation.community.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.admin.presentation.community.events.UpdateCommunityEffect
import com.newton.admin.presentation.community.events.UpdateCommunityEvent
import com.newton.admin.presentation.events.events.UpdateEvent
import com.newton.core.domain.models.about_us.Community
import com.newton.core.domain.models.admin_models.EventsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommunitySharedViewModel :ViewModel() {
    private val _selectedCommunity = MutableStateFlow<Community?>(null)
    private val selectedCommunity: StateFlow<Community?> = _selectedCommunity.asStateFlow()

    private val _uiState = MutableStateFlow<UpdateCommunityEffect>(UpdateCommunityEffect.Initial)
    val uiState: StateFlow<UpdateCommunityEffect> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            selectedCommunity.collect { community ->
                _uiState.value = when (community) {
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