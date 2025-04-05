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
