package com.newton.admin.data.remote

import com.newton.core.data.dto.EventApiResponse
import com.newton.core.data.dto.EventDto
import com.newton.core.data.dto.EventResponse
import com.newton.core.domain.models.event_models.AddEventRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import java.io.File

interface EventApi {
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
        @Part("name") name: String,
        @Part("category") category: String,
        @Part("description") description: String,
        @Part("image") image: File,
        @Part("date") date: String,
        @Part("location") location: String,
        @Part("organizer") organizer: String,
        @Part("contact_email") contactEmail: String,
        @Part("title") title: String,
        @Part("is_Virtual") isVirtual: Boolean
    ): EventApiResponse<EventDto>
}