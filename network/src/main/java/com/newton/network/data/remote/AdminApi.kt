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
package com.newton.network.data.remote

import com.newton.network.data.dto.admin.*
import com.newton.network.data.dto.events.*
import okhttp3.*
import retrofit2.http.*

interface AdminApi {
    @PATCH(AdminEndPoint.UPDATE_EVENT)
    suspend fun updateEvent(
        @Path("id") id: Int,
        @Body request: com.newton.network.domain.models.adminModels.UpdateEventRequest
    ): com.newton.network.ApiResponse<com.newton.network.domain.models.adminModels.EventsData>

    @DELETE(AdminEndPoint.DELETE_EVENT)
    suspend fun deleteEvent(
        @Path("id") id: Int
    ): EventResponse

    @Multipart
    @POST(AdminEndPoint.CREATE_EVENT)
    suspend fun createEvent(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part
    ): EventApiResponse<EventDto>

    @POST(AdminEndPoint.ADD_COMMUNITY)
    suspend fun addCommunity(
        @Body request: com.newton.network.domain.models.adminModels.AddCommunityRequest
    ): com.newton.network.ApiResponse<com.newton.network.domain.models.adminModels.CommunityData>

    @POST(AdminEndPoint.SEND_NEWSLETTER)
    suspend fun sendNewsLetter(
        @Body request: com.newton.network.domain.models.admin.NewsLetter
    ): com.newton.network.domain.models.admin.NewsLetterResponse

    @GET(AdminEndPoint.GET_RSVPS_DATA)
    suspend fun getAttendeeData(
        @Path("id") id: Int
    ): com.newton.network.ApiResponse<com.newton.network.PaginationResponse<AttendeeResponse>>

    @POST(AdminEndPoint.UPDATE_COMMUNITY)
    suspend fun updateCommunity(
        @Path("id") id: Int,
        @Body community: com.newton.network.domain.models.adminModels.UpdateCommunityRequest
    ): com.newton.network.domain.models.adminModels.CommunityData

    @POST(AdminEndPoint.ADD_EXECUTIVE)
    suspend fun addExecutive(
        @Body request: com.newton.network.domain.models.adminModels.ExecutiveRequest
    ): com.newton.network.ApiResponse<com.newton.network.domain.models.adminModels.ExecutiveResponse>

    @GET(AdminEndPoint.GET_USERS_FEEDBACK)
    suspend fun getUserFeedbacks(
        @Query("category") category: String? = null,
        @Query("ordering") order: String? = null,
        @Query("search") search: String? = null,
        @Query("status") status: String? = null
    ): com.newton.network.PaginationResponse<com.newton.network.domain.models.adminModels.UserFeedbackResponse>

    @GET(AdminEndPoint.GET_EVENTS_FEEDBACK)
    suspend fun getEventsFeedback(eventId: Int): com.newton.network.ApiResponse<com.newton.network.PaginationResponse<com.newton.network.domain.models.adminModels.EventsFeedback>>

    @GET(AdminEndPoint.GET_ALL_USERS_DATA)
    suspend fun getAllUsers(): com.newton.network.ApiResponse<List<com.newton.network.domain.models.adminModels.UserData>>

    @GET(AdminEndPoint.GET_ALL_EVENTS_DATA)
    suspend fun getAllEventsList(): com.newton.network.ApiResponse<com.newton.network.PaginationResponse<com.newton.network.domain.models.adminModels.EventsData>>

    @Multipart
    @POST(AdminEndPoint.ADD_PARTNER)
    suspend fun addPartner(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part logoField: MultipartBody.Part
    ): com.newton.network.ApiResponse<com.newton.network.domain.models.homeModels.PartnersData>

    @PATCH(AdminEndPoint.UPDATE_CLUB)
    suspend fun updateClub(clubRequest: com.newton.network.domain.models.adminModels.Club): com.newton.network.ApiResponse<com.newton.network.domain.models.adminModels.Club>
}
