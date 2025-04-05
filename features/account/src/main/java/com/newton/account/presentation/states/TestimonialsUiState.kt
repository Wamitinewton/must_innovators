package com.newton.account.presentation.states

sealed class TestimonialsUiState {
    data object Idle : TestimonialsUiState()

    data object Loading : TestimonialsUiState()

    data class Success(val message: String) : TestimonialsUiState()

    data class Error(val message: String) : TestimonialsUiState()
}
