package com.newton.admin.presentation.feedbacks.events

import com.newton.admin.domain.models.FeedBack
import com.newton.admin.domain.models.enums.FeedbackStatus

sealed class FeedbackEvent{
    data class IsLoadingChange(val isLoading:Boolean) : FeedbackEvent()
    data class IsSuccessChange(val isSuccess:Boolean) : FeedbackEvent()
    data class ErrorChange(val message: String) : FeedbackEvent()
    data class SearchQueryChange(val search: String) : FeedbackEvent()
    data class FeedbacksChange(val feedback: List<FeedBack>) : FeedbackEvent()
    data class SelectedFilterChange(val filter: FeedbackStatus) : FeedbackEvent()
    data object LoadFeedback: FeedbackEvent()
}
