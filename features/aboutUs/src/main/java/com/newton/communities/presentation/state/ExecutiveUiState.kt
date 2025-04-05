package com.newton.communities.presentation.state

import com.newton.core.domain.models.aboutUs.*

sealed class ExecutiveUiState {
    data object Loading : ExecutiveUiState()

    data class Success(val executives: List<Executive>) : ExecutiveUiState()

    data class Error(val message: String) : ExecutiveUiState()
}
