package com.newton.core.data.remote

import com.newton.core.domain.models.testimonials.*
import retrofit2.http.*

interface TestimonialsService {
    @POST(ApiEndpoints.CREATE_TESTIMONIAL)
    suspend fun createTestimonial(
        @Body request: CreateTestimonial
    ): TestimonialsData
}
