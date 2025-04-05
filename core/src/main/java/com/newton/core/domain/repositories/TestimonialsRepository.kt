package com.newton.core.domain.repositories

import com.newton.core.domain.models.testimonials.*
import com.newton.core.utils.*
import kotlinx.coroutines.flow.*

interface TestimonialsRepository {
    suspend fun createTestimonial(request: CreateTestimonial): Flow<Resource<TestimonialsData>>
}
