package com.newton.events.presentation.states

import com.newton.core.domain.models.admin_models.EventsData

sealed class SearchUiState {
    data object Initial : SearchUiState()

    data object Loading : SearchUiState()

    data class Success(val events: List<EventsData>) : SearchUiState()

    data class Error(val message: String) : SearchUiState()

    data object Empty : SearchUiState()
}