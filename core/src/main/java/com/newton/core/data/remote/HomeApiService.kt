package com.newton.core.data.remote

import com.newton.core.domain.models.homeModels.*
import com.newton.core.domain.models.testimonials.*
import retrofit2.http.*

interface HomeApiService {
    @GET(ApiEndpoints.GET_PARTNERS)
    suspend fun getPartners(): Partners

    @GET(ApiEndpoints.GET_TESTIMONIALS)
    suspend fun getTestimonials(): Testimonials
}
