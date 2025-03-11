package com.newton.admin.data.remote

import com.newton.core.data.response.events_response.EventApiResponse
import com.newton.core.data.response.events_response.EventDto
import com.newton.core.data.response.events_response.EventResponse
import com.newton.core.domain.models.ApiResponse
import com.newton.core.domain.models.PaginationResponse
import com.newton.core.domain.models.admin_models.AddCommunityRequest
import com.newton.core.domain.models.admin.NewsLetter
import com.newton.core.domain.models.admin.NewsLetterResponse
import com.newton.core.domain.models.admin_models.AddEventRequest
import com.newton.core.domain.models.admin_models.CommunityData
import com.newton.core.domain.models.admin_models.EventRegistrationData
import com.newton.core.domain.models.admin_models.FeedbackData
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
        @Part("name") name: RequestBody,
        @Part("category") category: RequestBody,
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("date") date: RequestBody,
        @Part("location") location: RequestBody,
        @Part("organizer") organizer: RequestBody,
        @Part("contact_email") contactEmail: RequestBody,
        @Part("title") title: RequestBody,
        @Part("is_virtual") isVirtual : RequestBody
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
    suspend fun getRsvpsData(
        @Path("id") id: Int
    ): ApiResponse<PaginationResponse<EventRegistrationData>>

    @POST(EventEndPoint.UPDATE_COMMUNITY)
    suspend fun updateCommunity(
        @Path("id") id: Int,
        @Body community: AddCommunityRequest
    ): ApiResponse<PaginationResponse<CommunityData>>
    @GET(EventEndPoint.EVENT_FEEDBACK_BY_ID)
    suspend fun getEventFeedbackBYId(
        @Path("id") id: Int,
    ): ApiResponse<FeedbackData>

    suspend fun getUserFeedbacks(): ApiResponse<PaginationResponse<FeedbackData>>


    @GET(EventEndPoint.GET_ALL_USERS_DATA)
    suspend fun getAllUsers(): ApiResponse<List<UserData>>
}