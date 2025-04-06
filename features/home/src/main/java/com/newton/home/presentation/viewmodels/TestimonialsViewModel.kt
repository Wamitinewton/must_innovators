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
package com.newton.home.presentation.viewmodels

import androidx.lifecycle.*
import com.newton.home.presentation.states.*
import com.newton.network.*
import com.newton.network.domain.repositories.*
import com.newton.shared.sharedBus.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class TestimonialsViewModel
@Inject
constructor(
    private val homeRepository: HomeRepository,
    private val testimonialsEventBus: TestimonialsEventBus
) : ViewModel() {
    private val _uiState = MutableStateFlow<TestimonialsUiState>(TestimonialsUiState.Initial)
    val uiState: StateFlow<TestimonialsUiState> = _uiState.asStateFlow()

    init {
        loadTestimonials()

        viewModelScope.launch {
            testimonialsEventBus.testimonialUpdate.collect {
                loadTestimonials()
            }
        }
    }

    private fun loadTestimonials() {
        viewModelScope.launch {
            _uiState.value = TestimonialsUiState.Loading
            homeRepository.getTestimonials().collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.value =
                            TestimonialsUiState.Error(
                                message = result.message ?: "An Unknown error occurred"
                            )
                    }

                    is Resource.Loading -> {
                        if (result.isLoading) {
                            _uiState.value = TestimonialsUiState.Loading
                        }
                    }

                    is Resource.Success -> {
                        _uiState.value =
                            TestimonialsUiState.Success(
                                testimonials = result.data ?: emptyList()
                            )
                    }
                }
            }
        }
    }

    fun retryLoadingTestimonials() {
        loadTestimonials()
    }
}
