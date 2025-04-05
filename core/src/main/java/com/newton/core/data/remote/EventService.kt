/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
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
