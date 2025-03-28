package com.newton.account.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.account.presentation.events.TestimonialsNavigationEvent
import com.newton.account.presentation.events.TestimonialsUiEvent
import com.newton.account.presentation.states.TestimonialsUiState
import com.newton.core.domain.models.testimonials.CreateTestimonial
import com.newton.core.domain.repositories.TestimonialsRepository
import com.newton.core.sharedBus.TestimonialsEventBus
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TestimonialsViewModel @Inject constructor(
    private val testimonialRepository: TestimonialsRepository,
    private val testimonialsEventBus: TestimonialsEventBus
) : ViewModel() {

    private val _uiState = MutableStateFlow<TestimonialsUiState>(TestimonialsUiState.Idle)
    val uiState: StateFlow<TestimonialsUiState> = _uiState.asStateFlow()

    private val _navigateToHome = Channel<TestimonialsNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigateToHome.receiveAsFlow()

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()

    private val _rating = MutableStateFlow(0)
    val rating: StateFlow<Int> = _rating.asStateFlow()


    fun handleEvent(event: TestimonialsUiEvent) {
        when (event) {
            is TestimonialsUiEvent.ContentChanged -> _content.value = event.content
            is TestimonialsUiEvent.RatingChanged -> _rating.value = event.rating
            is TestimonialsUiEvent.ClearError -> _uiState.value = TestimonialsUiState.Idle
            is TestimonialsUiEvent.Submit -> submitTestimonial()
        }
    }

    private fun submitTestimonial() {
        if (_content.value.isBlank()) {
            _uiState.value = TestimonialsUiState.Error("Testimonial content cannot be empty")
            return
        }
        if (_rating.value == 0) {
            _uiState.value = TestimonialsUiState.Error("Please provide a rating")
            return
        }

        viewModelScope.launch {
            val createTestimonial = CreateTestimonial(
                content = _content.value,
                rating = _rating.value
            )

            testimonialRepository.createTestimonial(createTestimonial).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.value =
                            TestimonialsUiState.Error(result.message ?: "Unknown Error")
                    }

                    is Resource.Loading -> {
                        if (result.isLoading) {
                            _uiState.value = TestimonialsUiState.Loading
                        }
                    }

                    is Resource.Success -> {
                       result.data?.let { handleTestimonialSuccess() }
                    }
                }
            }
        }
    }

    private suspend fun handleTestimonialSuccess() {
        try {
            _uiState.value = TestimonialsUiState.Success("Testimonial posted successfully")

            _navigateToHome.send(TestimonialsNavigationEvent.NavigateToHome)
            Timber.d("NAVIGATING..............TO HOME SCREEN")
            testimonialsEventBus.notifyTestimonialUpdate()
        } catch (e: Exception) {
            _uiState.value = TestimonialsUiState.Error("Failed to process testimonial: ${e.message}")
        }
    }
}