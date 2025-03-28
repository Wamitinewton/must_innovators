package com.newton.home.data

import com.newton.core.data.remote.HomeApiService
import com.newton.core.domain.models.home_models.PartnersData
import com.newton.core.domain.models.testimonials.TestimonialsData
import com.newton.core.domain.repositories.HomeRepository
import com.newton.core.utils.Resource
import com.newton.core.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApiService: HomeApiService
) : HomeRepository {
    override suspend fun getPartners(): Flow<Resource<List<PartnersData>>> = safeApiCall {
        val response =  homeApiService.getPartners().results
        response
    }

    override suspend fun getTestimonials(): Flow<Resource<List<TestimonialsData>>> = safeApiCall {
        val response = homeApiService.getTestimonials().results
        response
    }
}