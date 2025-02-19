package com.newton.home.presentation.states

import com.newton.home.domain.models.Testimonial

data class TestimonialsState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val testimonials: List<Testimonial> = emptyList(),
)