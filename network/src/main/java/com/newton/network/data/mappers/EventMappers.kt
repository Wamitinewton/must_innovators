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
package com.newton.network.data.mappers

import com.newton.network.data.dto.admin.*
import com.newton.network.data.dto.events.*

fun EventDto.toDomainEvent(): com.newton.network.domain.models.adminModels.EventsData {
    return com.newton.network.domain.models.adminModels.EventsData(
        id = id,
        imageUrl = image_url,
        name = name,
        category = category,
        description = description,
        date = date,
        location = location,
        organizer = organizer,
        contactEmail = contact_email,
        isVirtual = is_virtual
    )
}

fun EventRegistrationResponseDto.toEventRegistration(): RegistrationResponse {
    return RegistrationResponse(
        course = course,
        educationalLevel = educational_level,
        email = email,
        event = event,
        expectations = expectations,
        fullName = full_name,
        phoneNumber = phone_number,
        registrationTimestamp = registration_timestamp,
        ticketNumber = ticket_number,
        uid = uid,
        eventName = eventName,
        eventDescription = eventDescription,
        eventLocation = eventLocation,
        eventDate = eventDate
    )
}

fun List<EventDto>.toDomainEvents(): List<com.newton.network.domain.models.adminModels.EventsData> = map { it.toDomainEvent() }

fun List<EventRegistrationResponseDto>.toDomainUserTickets(): List<RegistrationResponse> =
    map { it.toEventRegistration() }
