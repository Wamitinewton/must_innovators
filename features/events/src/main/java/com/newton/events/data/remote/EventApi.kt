package com.newton.events.data.remote

import com.newton.events.data.remote.response.EventListResponse
import com.newton.events.data.remote.response.EventResponse
import com.newton.events.domain.models.Event
import com.newton.events.domain.models.EventRequest
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventApi {
    @GET(EventEndPoint.GET_EVENT_BY_ID)
    suspend fun getEventById(
        @Path("id") id: Int
    ): EventResponse

    @GET(EventEndPoint.GET_ALL_EVENTS)
    suspend fun getAllEvents(): EventListResponse

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