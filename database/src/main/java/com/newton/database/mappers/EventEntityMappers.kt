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
package com.newton.database.mappers

import com.newton.core.data.response.admin.*
import com.newton.core.domain.models.adminModels.*
import com.newton.database.entities.*

fun EventEntity.toDomainEvent() =
    com.newton.network.domain.models.adminModels.EventsData(
        id = id,
        imageUrl = imageUrl,
        name = name,
        category = category,
        description = description,
        date = date,
        location = location,
        organizer = organizer,
        contactEmail = contactEmail,
        isVirtual = isVirtual
    )

fun com.newton.network.domain.models.adminModels.EventsData.toEntity(pageNumber: Int) =
    EventEntity(
        id = id,
        imageUrl = imageUrl,
        name = name,
        category = category,
        description = description,
        date = date,
        location = location,
        organizer = organizer,
        contactEmail = contactEmail,
        isVirtual = isVirtual,
        pageNumber = pageNumber
    )

fun com.newton.network.domain.models.adminModels.EventsData.toEventEntity() =
    EventEntity(
        id = id,
        imageUrl = imageUrl,
        name = name,
        category = category,
        description = description,
        date = date,
        location = location,
        organizer = organizer,
        contactEmail = contactEmail,
        isVirtual = isVirtual
    )

fun TicketsEntity.toRegistrationResponse() =
    com.newton.network.data.response.admin.RegistrationResponse(
        course = course,
        educationalLevel = educationalLevel,
        email = email,
        event = event,
        expectations = expectations,
        fullName = fullName,
        phoneNumber = phoneNumber,
        registrationTimestamp = registrationTimestamp,
        ticketNumber = ticketNumber,
        uid = uid,
        eventName = eventName,
        eventDescription = eventDescription,
        eventLocation = eventLocation,
        eventDate = eventDate,
        isUsed = isUsed
    )

fun com.newton.network.data.response.admin.RegistrationResponse.toTicketEntity() =
    TicketsEntity(
        course = course,
        educationalLevel = educationalLevel,
        email = email,
        event = event,
        expectations = expectations,
        fullName = fullName,
        phoneNumber = phoneNumber,
        registrationTimestamp = registrationTimestamp,
        ticketNumber = ticketNumber,
        uid = uid,
        eventName = eventName,
        eventDescription = eventDescription,
        eventLocation = eventLocation,
        eventDate = eventDate,
        isUsed = isUsed
    )

fun List<com.newton.network.domain.models.adminModels.EventsData>.toEventsEntity(): List<EventEntity> = map { it.toEventEntity() }

fun List<com.newton.network.data.response.admin.RegistrationResponse>.toUserTicketsEntity(): List<TicketsEntity> =
    map { it.toTicketEntity() }

fun List<EventEntity>.toEventDataList(): List<com.newton.network.domain.models.adminModels.EventsData> = map { it.toDomainEvent() }
