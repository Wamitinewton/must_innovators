package com.newton.admin.presentation.feedbacks.states

sealed class FeedbackState {
    data object Loading : FeedbackState()
    data object Success : FeedbackState()
    data class Error(val message: String) : FeedbackState()
}
