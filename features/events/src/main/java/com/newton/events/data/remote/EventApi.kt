package com.newton.events.data.remote

import com.newton.core.data.dto.EventRegistrationResponse
import com.newton.core.data.dto.EventResponse
import com.newton.events.domain.models.EventRegistrationRequest
import com.newton.events.domain.models.EventRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApi {

    @POST(EventEndPoint.RSVP_EVENT)
    suspend fun registerForEvent(
        @Path("id") eventId: Int,
        @Body registrationRequest: EventRegistrationRequest
    ): EventRegistrationResponse

    @GET(EventEndPoint.GET_ALL_EVENTS)
    suspend fun getAllEvents(
        @Query("page") page: Int = 1,
        @Query("page_siz") pageSize: Int = 10
    ): com.newton.core.data.dto.EventApiResponse<EventResponse>


}