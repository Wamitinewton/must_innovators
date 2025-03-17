package com.newton.home.presentation.states

import com.newton.core.domain.models.home_models.PartnersData

sealed class PartnersUiState {
    data object Loading : PartnersUiState()
    data class Success(val partners: List<PartnersData>) : PartnersUiState()
    data class Error(val message: String) : PartnersUiState()
    data object Empty : PartnersUiState()
}