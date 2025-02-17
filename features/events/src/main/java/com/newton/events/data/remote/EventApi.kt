package com.newton.events.data.remote

import com.newton.core.data.dto.EventResponse
import com.newton.events.domain.models.EventRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface EventApi {
    @GET(EventEndPoint.GET_EVENT_BY_ID)
    suspend fun getEventById(
        @Path("id") id: Int
    ): EventResponse

    @GET(EventEndPoint.GET_ALL_EVENTS)
    suspend fun getAllEvents(
        @Query("page") page: Int = 1,
        @Query("page_siz") pageSize: Int = 10
    ): com.newton.core.data.dto.EventApiResponse<EventResponse>

    @PUT(EventEndPoint.UPDATE_EVENT)
    suspend fun updateEvent(
        @Path("id") id: Int,
        @Body request: EventRequest
    ): EventResponse

    @DELETE(EventEndPoint.DELETE_EVENT)
    suspend fun deleteEvent(
        @Path("id") id: Int
    ): EventResponse

    @POST(EventEndPoint.CREATE_EVENT)
    suspend fun createEvent(
        @Body request: EventRequest
    ): EventResponse
}