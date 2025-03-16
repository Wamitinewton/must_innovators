package com.newton.admin.data.remote

import com.newton.core.data.response.events_response.EventApiResponse
import com.newton.core.data.response.events_response.EventDto
import com.newton.core.data.response.events_response.EventResponse
import com.newton.core.domain.models.ApiResponse
import com.newton.core.domain.models.PaginationResponse
import com.newton.core.domain.models.admin.NewsLetter
import com.newton.core.domain.models.admin.NewsLetterResponse
import com.newton.core.domain.models.admin_models.AddCommunityRequest
import com.newton.core.domain.models.admin_models.AddEventRequest
import com.newton.core.domain.models.admin_models.Attendees
import com.newton.core.domain.models.admin_models.CommunityData
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.domain.models.admin_models.EventsFeedback
import com.newton.core.domain.models.admin_models.FeedbackData
import com.newton.core.domain.models.admin_models.PartnersResponse
import com.newton.core.domain.models.admin_models.UserData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface AdminApi {
    @PUT(EventEndPoint.UPDATE_EVENT)
    suspend fun updateEvent(
        @Path("id") id: Int,
        @Body request: AddEventRequest
    ): EventResponse

    @DELETE(EventEndPoint.DELETE_EVENT)
    suspend fun deleteEvent(
        @Path("id") id: Int
    ): EventResponse

    @Multipart
    @POST(EventEndPoint.CREATE_EVENT)
    suspend fun createEvent(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part
    ): EventApiResponse<EventDto>

    @POST(EventEndPoint.ADD_COMMUNITY)
    suspend fun addCommunity(
        @Body request: AddCommunityRequest
    ): ApiResponse<CommunityData>

    @POST(EventEndPoint.SEND_NEWSLETTER)
    suspend fun sendNewsLetter(
        @Body request:NewsLetter
    ):NewsLetterResponse

    @GET(EventEndPoint.GET_RSVPS_DATA)
    suspend fun getAttendeeData(
        @Path("id") id: Int
    ): ApiResponse<PaginationResponse<Attendees>>

    @POST(EventEndPoint.UPDATE_COMMUNITY)
    suspend fun updateCommunity(
        @Path("id") id: Int,
        @Body community: AddCommunityRequest
    ): ApiResponse<PaginationResponse<CommunityData>>

    suspend fun getUserFeedbacks(): ApiResponse<PaginationResponse<FeedbackData>>

    @GET(EventEndPoint.GET_EVENTS_FEEDBACK)
    suspend fun getEventsFeedback(eventId:Int): ApiResponse<PaginationResponse<EventsFeedback>>

    @GET(EventEndPoint.GET_ALL_USERS_DATA)
    suspend fun getAllUsers(): ApiResponse<PaginationResponse<UserData>>

    @GET(EventEndPoint.GET_ALL_EVENTS_DATA)
    suspend fun getAllEventsList(): ApiResponse<PaginationResponse<EventsData>>

    @Multipart
    @POST(EventEndPoint.ADD_PARTNER)
    suspend fun addPartner(
        @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part
    ): ApiResponse<PartnersResponse>
}