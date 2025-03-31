package com.newton.core.data.remote

import com.newton.core.data.response.events.EventApiResponse
import com.newton.core.data.response.events.EventDto
import com.newton.core.data.response.events.EventRegistrationResponse
import com.newton.core.data.response.events.EventResponse
import com.newton.core.data.response.events.UserTicketsResponse
import com.newton.core.domain.models.event_models.EventRegistrationRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
    suspend fun getUserTickets(
    ): UserTicketsResponse

}