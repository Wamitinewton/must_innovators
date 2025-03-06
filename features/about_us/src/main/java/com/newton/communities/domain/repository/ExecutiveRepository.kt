package com.newton.communities.domain.repository

import com.newton.core.domain.models.about_us.Executive
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ExecutiveRepository {
    suspend fun getExecutives(): Flow<Resource<List<Executive>>>
}