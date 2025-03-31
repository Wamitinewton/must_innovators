package com.newton.core.domain.repositories

import com.newton.core.domain.models.ApiResponse
import com.newton.core.domain.models.admin.NewsLetter
import com.newton.core.domain.models.admin.NewsLetterResponse
import com.newton.core.domain.models.admin_models.AddCommunityRequest
import com.newton.core.domain.models.admin_models.AddEventRequest
import com.newton.core.domain.models.admin_models.AddPartnerRequest
import com.newton.core.domain.models.admin_models.Attendee
import com.newton.core.domain.models.admin_models.Club
import com.newton.core.domain.models.admin_models.CommunityData
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.domain.models.admin_models.EventsFeedback
import com.newton.core.domain.models.admin_models.ExecutiveRequest
import com.newton.core.domain.models.admin_models.ExecutiveResponse
import com.newton.core.domain.models.admin_models.FeedbackData
import com.newton.core.domain.models.admin_models.UpdateCommunityRequest
import com.newton.core.domain.models.admin_models.UpdateEventRequest
import com.newton.core.domain.models.admin_models.UserData
import com.newton.core.domain.models.home_models.PartnersData
import com.newton.core.enums.FeedbackStatus
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
    suspend fun addEvent(event: AddEventRequest): Flow<Resource<EventsData>>
    suspend fun addCommunity(community: AddCommunityRequest): Flow<Resource<ApiResponse<CommunityData>>>
    suspend fun updateCommunity(communityId:Int,community: UpdateCommunityRequest): Flow<Resource<CommunityData>>
    suspend fun updateEvent(event: UpdateEventRequest,eventId:Int): Flow<Resource<EventsData>>
    suspend fun sendNewsLetter(newsLetter: NewsLetter): Flow<Resource<NewsLetterResponse>>
    suspend fun getEventFeedbackBYId(
        eventId: Int,
        isRefresh: Boolean
    ): Flow<Resource<List<EventsFeedback>>>

    suspend fun getAllFeedbacks(
        isRefresh: Boolean,
        category: String? = null,
        ordering: String? = null,
        status: FeedbackStatus? = null,
        search: String? = null
    ): Flow<Resource<List<FeedbackData>>>

    suspend fun getAllUsers(isRefresh: Boolean): Flow<Resource<List<UserData>>>
    suspend fun getRegistrationList(eventId: Int): Flow<Resource<List<Attendee>>>
    suspend fun getListOfEvents(): Flow<Resource<List<EventsData>>>
    suspend fun addPartner(partners: AddPartnerRequest): Flow<Resource<PartnersData>>
    suspend fun updateExecutive(executive: ExecutiveRequest):Flow<Resource<ExecutiveResponse>>
    suspend fun updateClub(clubRequest: Club):Flow<Resource<Club>>
}