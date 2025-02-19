package com.newton.home.domain.repository

import com.newton.core.domain.models.event_models.EventsData
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getPagedEvents(): Flow<Resource<EventsData>>
}