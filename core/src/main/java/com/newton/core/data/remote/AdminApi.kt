package com.newton.core.data.remote

import com.newton.core.data.response.admin.*
import com.newton.core.data.response.events.*
import com.newton.core.domain.models.*
import com.newton.core.domain.models.admin.*
import com.newton.core.domain.models.adminModels.*
import com.newton.core.domain.models.homeModels.*
import okhttp3.*
import retrofit2.http.*

interface AdminApi {
    @PATCH(AdminEndPoint.UPDATE_EVENT)
    suspend fun updateEvent(
        @Path("id") id: Int,
        @Body request: UpdateEventRequest
    ): ApiResponse<EventsData>

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
        @Body request: AddCommunityRequest
    ): ApiResponse<CommunityData>

    @POST(AdminEndPoint.SEND_NEWSLETTER)
    suspend fun sendNewsLetter(
        @Body request: NewsLetter
    ): NewsLetterResponse

    @GET(AdminEndPoint.GET_RSVPS_DATA)
    suspend fun getAttendeeData(
        @Path("id") id: Int
    ): ApiResponse<PaginationResponse<AttendeeResponse>>

    @POST(AdminEndPoint.UPDATE_COMMUNITY)
    suspend fun updateCommunity(
        @Path("id") id: Int,
        @Body community: UpdateCommunityRequest
    ): CommunityData

    @POST(AdminEndPoint.ADD_EXECUTIVE)
    suspend fun addExecutive(
        @Body request: ExecutiveRequest
    ): ApiResponse<ExecutiveResponse>

    @GET(AdminEndPoint.GET_USERS_FEEDBACK)
    suspend fun getUserFeedbacks(
        @Query("category") category: String? = null,
        @Query("ordering") order: String? = null,
        @Query("search") search: String? = null,
        @Query("status") status: String? = null
    ): PaginationResponse<UserFeedbackResponse>

    @GET(AdminEndPoint.GET_EVENTS_FEEDBACK)
    suspend fun getEventsFeedback(eventId: Int): ApiResponse<PaginationResponse<EventsFeedback>>

    @GET(AdminEndPoint.GET_ALL_USERS_DATA)
    suspend fun getAllUsers(): ApiResponse<List<UserData>>

    @GET(AdminEndPoint.GET_ALL_EVENTS_DATA)
    suspend fun getAllEventsList(): ApiResponse<PaginationResponse<EventsData>>

    @Multipart
    @POST(AdminEndPoint.ADD_PARTNER)
    suspend fun addPartner(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part logo_field: MultipartBody.Part
    ): ApiResponse<PartnersData>

    @PATCH(AdminEndPoint.UPDATE_CLUB)
    suspend fun updateClub(clubRequest: Club): ApiResponse<Club>
}
