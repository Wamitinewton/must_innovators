package com.newton.admin.presentation.feedbacks.events

import com.newton.core.domain.models.adminModels.*
import com.newton.core.enums.*

sealed class FeedbackEvent {
    data class IsLoadingChange(val isLoading: Boolean) : FeedbackEvent()

    data class IsSuccessChange(val isSuccess: Boolean) : FeedbackEvent()

    data class ErrorChange(val message: String) : FeedbackEvent()

    data class SearchQueryChange(val search: String) : FeedbackEvent()

    data class FeedbacksChange(val feedback: List<FeedbackData>) : FeedbackEvent()

    data class SelectedFilterChange(val filter: FeedbackStatus?) : FeedbackEvent()

    data object LoadFeedback : FeedbackEvent()
}
