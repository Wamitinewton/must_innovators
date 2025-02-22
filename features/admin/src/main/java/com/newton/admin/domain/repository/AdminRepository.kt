package com.newton.admin.domain.repository

import com.newton.core.domain.models.event_models.AddEventRequest
import com.newton.core.domain.models.event_models.EventsData
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
    suspend fun addEvent(event: AddEventRequest): Flow<Resource<EventsData>>
}