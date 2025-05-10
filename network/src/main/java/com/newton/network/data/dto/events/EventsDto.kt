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
package com.newton.network.data.dto.events

import kotlinx.serialization.*

@Serializable
data class EventApiResponse<T>(
    val message: String,
    val status: String,
    val data: T
)

@Serializable
data class EventResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<EventDto>
)

@Serializable
data class EventDto(
    val id: Int,
    val image_url: String,
    val name: String,
    val category: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val organizer: String,
    val contact_email: String,
    val is_virtual: Boolean
)
