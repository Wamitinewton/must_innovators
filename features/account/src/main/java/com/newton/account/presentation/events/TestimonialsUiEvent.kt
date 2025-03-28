package com.newton.account.presentation.events

sealed class TestimonialsUiEvent {
    data object Submit : TestimonialsUiEvent()
    data object ClearError : TestimonialsUiEvent()
    data class ContentChanged(val content: String) : TestimonialsUiEvent()
    data class RatingChanged(val rating: Int) : TestimonialsUiEvent()
}