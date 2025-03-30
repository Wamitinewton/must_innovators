package com.newton.core.data.remote

import com.newton.core.data.response.events.EventApiResponse
import com.newton.core.data.response.events.EventDto
import com.newton.core.data.response.events.EventResponse
import com.newton.core.domain.models.ApiResponse
import com.newton.core.domain.models.PaginationResponse
import com.newton.core.domain.models.admin.NewsLetter
import com.newton.core.domain.models.admin.NewsLetterResponse
import com.newton.core.domain.models.admin_models.AddCommunityRequest
import com.newton.core.data.response.admin.AttendeeResponse
import com.newton.core.domain.models.admin_models.Club
import com.newton.core.domain.models.admin_models.CommunityData
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.domain.models.admin_models.EventsFeedback
import com.newton.core.domain.models.admin_models.ExecutiveRequest
import com.newton.core.domain.models.admin_models.ExecutiveResponse
import com.newton.core.domain.models.admin_models.UpdateCommunityRequest
import com.newton.core.domain.models.admin_models.UpdateEventRequest
import com.newton.core.domain.models.admin_models.UserData
import com.newton.core.domain.models.admin_models.UserFeedbackResponse
import com.newton.core.domain.models.home_models.PartnersData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

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
        @Body request:NewsLetter
    ):NewsLetterResponse

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
    suspend fun getEventsFeedback(eventId:Int): ApiResponse<PaginationResponse<EventsFeedback>>

    @GET(AdminEndPoint.GET_ALL_USERS_DATA)
    suspend fun getAllUsers(): ApiResponse<List<UserData>>

    @GET(AdminEndPoint.GET_ALL_EVENTS_DATA)
    suspend fun getAllEventsList(): ApiResponse<PaginationResponse<EventsData>>

    @Multipart
    @POST(AdminEndPoint.ADD_PARTNER)
    suspend fun addPartner(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part  logo_field : MultipartBody.Part
    ): ApiResponse<PartnersData>

    @PATCH(AdminEndPoint.UPDATE_CLUB)
    suspend fun updateClub(clubRequest: Club): ApiResponse<Club>
}