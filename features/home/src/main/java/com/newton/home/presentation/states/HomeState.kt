package com.newton.home.presentation.states

import com.newton.core.domain.models.home_models.PartnersData
import com.newton.core.domain.models.home_models.TestimonialsData

sealed class PartnersUiState {
    data object Loading : PartnersUiState()
    data class Success(val partners: List<PartnersData>) : PartnersUiState()
    data class Error(val message: String) : PartnersUiState()
    data object Empty : PartnersUiState()
}

sealed class TestimonialsUiState {
    data object Loading : TestimonialsUiState()
    data class Success(val testimonials: List<TestimonialsData>) : TestimonialsUiState()
    data class Error(val message: String) : TestimonialsUiState()
    data object Initial : TestimonialsUiState()
}