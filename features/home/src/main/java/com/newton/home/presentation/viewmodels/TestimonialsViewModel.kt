package com.newton.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.domain.repositories.HomeRepository
import com.newton.core.sharedBus.TestimonialsEventBus
import com.newton.core.utils.Resource
import com.newton.home.presentation.states.TestimonialsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestimonialsViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val testimonialsEventBus: TestimonialsEventBus
): ViewModel() {

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
                when(result) {
                    is Resource.Error -> {
                        _uiState.value = TestimonialsUiState.Error(
                            message = result.message ?: "An Unknown error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        if (result.isLoading) {
                            _uiState.value = TestimonialsUiState.Loading
                        }
                    }
                    is Resource.Success -> {
                        _uiState.value = TestimonialsUiState.Success(
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