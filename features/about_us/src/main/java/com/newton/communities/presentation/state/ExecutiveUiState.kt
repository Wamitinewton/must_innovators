package com.newton.communities.presentation.state

import com.newton.core.domain.models.about_us.Community
import com.newton.core.domain.models.about_us.Executive

data class ExecutiveUiState(
    val isLoading: Boolean = false,
    val communities: List<Executive> = emptyList(),
    val errorMessage: String? = null,
)
