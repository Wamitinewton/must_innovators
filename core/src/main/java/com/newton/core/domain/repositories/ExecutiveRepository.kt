package com.newton.core.domain.repositories

import com.newton.core.domain.models.aboutUs.*
import com.newton.core.utils.*
import kotlinx.coroutines.flow.*

interface ExecutiveRepository {
    suspend fun getExecutives(): Flow<Resource<List<Executive>>>
}
