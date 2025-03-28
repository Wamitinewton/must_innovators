package com.newton.core.domain.models.testimonials

import kotlinx.serialization.Serializable

@Serializable
data class CreateTestimonial(
    val content: String,
    val rating: Int
)