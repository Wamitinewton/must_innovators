package com.newton.database.mappers

import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.domain.models.admin_models.RegistrationResponse
import com.newton.database.entities.EventEntity
import com.newton.database.entities.TicketsEntity


fun EventEntity.toDomainEvent() = EventsData(
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

fun EventsData.toEntity(pageNumber: Int) = EventEntity(
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

fun EventsData.toEventEntity() = EventEntity(
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
)

fun TicketsEntity.toRegistrationResponse() = RegistrationResponse(
    course = course,
    educationalLevel = educationalLevel,
    email = email,
    event = event,
    expectations = expectations,
    fullName = fullName,
    phoneNumber = phoneNumber,
    registrationTimestamp = registrationTimestamp,
    ticketNumber = ticketNumber,
    uid = uid
)

fun RegistrationResponse.toTicketEntity() = TicketsEntity(
    course = course,
    educationalLevel = educationalLevel,
    email = email,
    event = event,
    expectations = expectations,
    fullName = fullName,
    phoneNumber = phoneNumber,
    registrationTimestamp = registrationTimestamp,
    ticketNumber = ticketNumber,
    uid = uid
)


fun List<EventsData>.toEventsEntity(): List<EventEntity> =
    map { it.toEventEntity() }

fun List<RegistrationResponse>.toUserTicketsEntity(): List<TicketsEntity> =
    map { it.toTicketEntity() }

fun List<EventEntity>.toEventDataList():List<EventsData> = map { it.toDomainEvent() }

