package com.newton.communities.presentation.state

import com.newton.core.domain.models.about_us.Community

sealed class CommunitiesUiState {
    data class Loading(val isRefreshing: Boolean) : CommunitiesUiState()

    data class Content(
        val communities: List<Community>,
        val isRefreshing: Boolean
    ) : CommunitiesUiState()

    data class Error(
        val message: String,
        val communities: List<Community> = emptyList()
    ) : CommunitiesUiState()
}

