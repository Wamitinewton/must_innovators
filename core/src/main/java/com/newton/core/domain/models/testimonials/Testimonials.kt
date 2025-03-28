package com.newton.core.domain.models.testimonials

import kotlinx.serialization.Serializable

@Serializable
data class Testimonials(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<TestimonialsData>
)

@Serializable
data class TestimonialsData(
    val content: String,
    val created_at: String,
    val id: Int,
    val rating: Int,
    val status: String,
    val user: Int,
    val user_name: String
)