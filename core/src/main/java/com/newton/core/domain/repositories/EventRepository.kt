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
package com.newton.core.domain.repositories

import androidx.paging.*
import com.newton.core.data.response.admin.*
import com.newton.core.domain.models.adminModels.*
import com.newton.core.domain.models.eventModels.*
import com.newton.core.utils.*
import kotlinx.coroutines.flow.*

interface EventRepository {
    fun getPagedEvents(): Flow<PagingData<EventsData>>

    suspend fun getEventById(id: Int): Flow<Resource<Event>>

    suspend fun registerForEvent(
        eventId: Int,
        registrationRequest: EventRegistrationRequest
    ): Flow<Resource<RegistrationResponse>>

    suspend fun searchEvents(eventName: String): Flow<Resource<List<EventsData>>>

    suspend fun getLatestEvents(count: Int): Flow<Resource<List<EventsData>>>

    suspend fun getUserTickets(): Flow<Resource<List<RegistrationResponse>>>
}
