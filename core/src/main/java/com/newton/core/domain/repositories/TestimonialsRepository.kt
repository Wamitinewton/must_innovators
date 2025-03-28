package com.newton.core.domain.repositories

import com.newton.core.domain.models.testimonials.CreateTestimonial
import com.newton.core.domain.models.testimonials.TestimonialsData
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TestimonialsRepository {
    suspend fun createTestimonial(request: CreateTestimonial): Flow<Resource<TestimonialsData>>
}