package com.newton.core.data.remote

import com.newton.core.data.response.events.*
import com.newton.core.domain.models.eventModels.*
import retrofit2.http.*

interface EventService {
    @POST(ApiEndpoints.RSVP_EVENT)
    suspend fun registerForEvent(
        @Path("id") eventId: Int,
        @Body registrationRequest: EventRegistrationRequest
    ): EventRegistrationResponse

    @GET(ApiEndpoints.GET_ALL_EVENTS)
    suspend fun getAllEvents(
        @Query("page") page: Int = 1,
        @Query("page_siz") pageSize: Int = 10
    ): EventApiResponse<EventResponse>

    @GET(ApiEndpoints.SEARCH_EVENT)
    suspend fun searchEvents(
        @Query("name") eventName: String
    ): EventApiResponse<List<EventDto>>

    @GET(ApiEndpoints.GET_USER_TICKETS)
    suspend fun getUserTickets(): UserTicketsResponse
}
