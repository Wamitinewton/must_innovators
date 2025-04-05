package com.newton.account.data.repository

import com.newton.core.data.remote.*
import com.newton.core.domain.models.testimonials.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import com.newton.database.dao.*
import com.newton.database.entities.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class TestimonialRepositoryImpl
@Inject
constructor(
    private val testimonialsService: TestimonialsService,
    private val testimonialsDao: TestimonialsDao
) : TestimonialsRepository {
    override suspend fun createTestimonial(request: CreateTestimonial): Flow<Resource<TestimonialsData>> =
        safeApiCall {
            val response = testimonialsService.createTestimonial(request)

            val testimonialEntity =
                TestimonialsEntity(
                    id = response.id,
                    content = response.content,
                    rating = response.rating,
                    status = response.status,
                    user = response.user,
                    userName = response.user_name,
                    createdAt = response.created_at
                )

            testimonialsDao.insertTestimonial(testimonialEntity)

            TestimonialsData(
                content = response.content,
                created_at = response.created_at,
                id = response.id,
                rating = response.rating,
                status = response.status,
                user = response.user,
                user_name = response.user_name
            )
        }
}
