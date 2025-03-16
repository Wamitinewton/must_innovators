package com.newton.core.domain.repositories

import com.newton.core.domain.models.home_models.PartnersData
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getPartners(): Flow<Resource<List<PartnersData>>>
}