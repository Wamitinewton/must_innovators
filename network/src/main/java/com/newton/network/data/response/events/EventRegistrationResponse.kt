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
package com.newton.network.data.response.events

import kotlinx.serialization.*

@Serializable
data class EventRegistrationResponse(
    val data: EventRegistrationResponseDto,
    val message: String,
    val status: String
)

@Serializable
data class UserTicketsResponse(
    val data: List<EventRegistrationResponseDto>,
    val message: String,
    val status: String
)

@Serializable
data class EventRegistrationResponseDto(
    val eventName: String,
    val eventDescription: String,
    val eventLocation: String,
    val eventDate: String,
    val course: String,
    val educational_level: String,
    val email: String,
    val event: Int,
    val expectations: String,
    val full_name: String,
    val phone_number: String,
    val registration_timestamp: String,
    val ticket_number: String,
    val uid: String
)
