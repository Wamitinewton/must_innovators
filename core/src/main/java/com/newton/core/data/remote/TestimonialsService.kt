package com.newton.core.data.remote

import com.newton.core.domain.models.testimonials.CreateTestimonial
import com.newton.core.domain.models.testimonials.Testimonials
import com.newton.core.domain.models.testimonials.TestimonialsData
import retrofit2.http.Body
import retrofit2.http.POST

interface TestimonialsService {

    @POST(ApiEndpoints.CREATE_TESTIMONIAL)
    suspend fun createTestimonial(
        @Body request: CreateTestimonial
    ): TestimonialsData
}