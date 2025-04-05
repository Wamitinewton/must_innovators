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
package com.newton.home.data

import com.newton.core.data.remote.*
import com.newton.core.domain.models.homeModels.*
import com.newton.core.domain.models.testimonials.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class HomeRepositoryImpl
@Inject
constructor(
    private val homeApiService: HomeApiService
) : HomeRepository {
    override suspend fun getPartners(): Flow<Resource<List<PartnersData>>> =
        safeApiCall {
            val response = homeApiService.getPartners().results
            response
        }

    override suspend fun getTestimonials(): Flow<Resource<List<TestimonialsData>>> =
        safeApiCall {
            val response = homeApiService.getTestimonials().results
            response
        }
}
