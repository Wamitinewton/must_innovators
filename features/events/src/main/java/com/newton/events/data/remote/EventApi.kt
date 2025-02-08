package com.newton.events.data.remote

import com.newton.events.domain.models.Event
import com.newton.events.domain.models.EventRequest
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventService {
    @GET("events/{id}")
    suspend fun getEventById(
        @Path("id") id: Int
    ): Response<Event>

    @GET("events")
    suspend fun getAllEvents(): Response<List<Event>>

    @PUT("events/{id}")
    suspend fun updateEvent(
        @Path("id") id: Int,
        @Body request: EventRequest
    ): Response<Event>

    @DELETE("events/{id}")
    suspend fun deleteEvent(
        @Path("id") id: Int
    ): Response<Unit>

    @POST("events")
    suspend fun createEvent(
        @Body request: EventRequest
    ): Response<Event>
}