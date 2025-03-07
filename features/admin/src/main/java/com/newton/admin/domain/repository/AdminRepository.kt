package com.newton.admin.domain.repository

import com.newton.admin.domain.models.AddCommunityRequest
import com.newton.admin.domain.models.NewsLetter
import com.newton.admin.domain.models.NewsLetterResponse
import com.newton.core.domain.models.ApiResponse
import com.newton.core.domain.models.PaginationResponse
import com.newton.core.domain.models.admin_models.CommunityData
import com.newton.core.domain.models.admin_models.EventRegistrationData
import com.newton.core.domain.models.admin_models.FeedbackData
import com.newton.core.domain.models.event_models.AddEventRequest
import com.newton.core.domain.models.event_models.EventsData
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
    suspend fun addEvent(event: AddEventRequest): Flow<Resource<EventsData>>
    suspend fun addCommunity(community: AddCommunityRequest): Flow<Resource<ApiResponse<CommunityData>>>
    suspend fun updateCommunity(community: AddCommunityRequest): Flow<Resource<CommunityData>>
    suspend fun sendNewsLetter(newsLetter: NewsLetter): Flow<Resource<NewsLetterResponse>>
    suspend fun getRsvpsData(eventId: Int): Flow<Resource<ApiResponse<PaginationResponse<EventRegistrationData>>>>
    suspend fun getEventFeedbackBYId(eventId: Int): Flow<Resource<ApiResponse<PaginationResponse<FeedbackData>>>>
}