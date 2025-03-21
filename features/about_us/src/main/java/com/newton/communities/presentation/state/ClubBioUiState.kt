package com.newton.communities.presentation.state

import com.newton.core.domain.models.about_us.ClubBioData

sealed class ClubBioUiState {
    data object Loading : ClubBioUiState()
    data class Success(val data: ClubBioData) : ClubBioUiState()
    data class Error(val message: String) : ClubBioUiState()
}