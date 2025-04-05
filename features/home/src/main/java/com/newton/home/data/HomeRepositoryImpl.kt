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
