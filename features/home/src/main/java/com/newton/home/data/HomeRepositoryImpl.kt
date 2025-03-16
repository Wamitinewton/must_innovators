package com.newton.home.data

import com.newton.core.data.remote.HomeApiService
import com.newton.core.domain.models.home_models.PartnersData
import com.newton.core.domain.repositories.HomeRepository
import com.newton.core.utils.Resource
import com.newton.core.utils.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val partnersService: HomeApiService
) : HomeRepository {
    override suspend fun getPartners(): Flow<Resource<List<PartnersData>>> = safeApiCall {
        val response =  partnersService.getPartners().results
        response
    }
}