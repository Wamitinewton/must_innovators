package com.newton.events.presentation.states

import com.newton.core.data.response.admin.*

sealed class UserTicketsUiState {
    data object Initial : UserTicketsUiState()

    data object Loading : UserTicketsUiState()

    data class Success(val tickets: List<RegistrationResponse>) : UserTicketsUiState()

    data class Error(val message: String) : UserTicketsUiState()
}
