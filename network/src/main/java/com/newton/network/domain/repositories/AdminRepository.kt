/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.network.domain.repositories

import com.newton.core.enums.*
import com.newton.network.*
import com.newton.network.domain.models.admin.*
import com.newton.network.domain.models.adminModels.*
import com.newton.network.domain.models.homeModels.*
import kotlinx.coroutines.flow.*

interface AdminRepository {
    suspend fun addEvent(event: AddEventRequest): Flow<com.newton.network.Resource<EventsData>>

    suspend fun addCommunity(community: AddCommunityRequest): Flow<com.newton.network.Resource<ApiResponse<CommunityData>>>

    suspend fun updateCommunity(
        communityId: Int,
        community: UpdateCommunityRequest
    ): Flow<com.newton.network.Resource<CommunityData>>

    suspend fun updateEvent(
        event: UpdateEventRequest,
        eventId: Int
    ): Flow<com.newton.network.Resource<EventsData>>

    suspend fun sendNewsLetter(newsLetter: NewsLetter): Flow<com.newton.network.Resource<NewsLetterResponse>>

    suspend fun getEventFeedbackBYId(
        eventId: Int,
        isRefresh: Boolean
    ): Flow<com.newton.network.Resource<List<EventsFeedback>>>

    suspend fun getAllFeedbacks(
        isRefresh: Boolean,
        category: String? = null,
        ordering: String? = null,
        status: FeedbackStatus? = null,
        search: String? = null
    ): Flow<com.newton.network.Resource<List<FeedbackData>>>

    suspend fun getAllUsers(isRefresh: Boolean): Flow<com.newton.network.Resource<List<UserData>>>

    suspend fun getRegistrationList(eventId: Int): Flow<com.newton.network.Resource<List<Attendee>>>

    suspend fun getListOfEvents(): Flow<com.newton.network.Resource<List<EventsData>>>

    suspend fun addPartner(partners: AddPartnerRequest): Flow<com.newton.network.Resource<PartnersData>>

    suspend fun updateExecutive(executive: ExecutiveRequest): Flow<com.newton.network.Resource<ExecutiveResponse>>

    suspend fun updateClub(clubRequest: Club): Flow<com.newton.network.Resource<Club>>
}
