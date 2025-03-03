package com.newton.communities.presentation.state

import com.newton.core.domain.models.about_us.Community

data class CommunitiesUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val communities: List<Community> = emptyList(),
    val errorMessage: String? = null,
)
