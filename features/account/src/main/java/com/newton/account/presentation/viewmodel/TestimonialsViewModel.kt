/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.account.presentation.viewmodel

import androidx.lifecycle.*
import com.newton.account.presentation.events.*
import com.newton.account.presentation.states.*
import com.newton.network.*
import com.newton.network.domain.models.testimonials.*
import com.newton.network.domain.repositories.*
import com.newton.shared.sharedBus.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import timber.log.*
import javax.inject.*

@HiltViewModel
class TestimonialsViewModel
@Inject
constructor(
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
            val createTestimonial =
                CreateTestimonial(
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
            _uiState.value =
                TestimonialsUiState.Error("Failed to process testimonial: ${e.message}")
        }
    }
}
