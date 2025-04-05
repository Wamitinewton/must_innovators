package com.newton.admin.presentation.events.states

import com.newton.core.domain.models.adminModels.*

data class AdminFeedbackState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val hasError: String? = null,
    val feedbacks: List<EventsFeedback> = emptyList()
)
