package com.newton.core.data.remote

import com.newton.core.domain.models.home_models.Partners
import retrofit2.http.GET

interface HomeApiService {

    @GET(ApiEndpoints.GET_PARTNERS)
    suspend fun getPartners(): Partners
}