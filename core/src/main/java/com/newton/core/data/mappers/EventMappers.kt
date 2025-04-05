package com.newton.core.data.mappers

import com.newton.core.data.response.admin.*
import com.newton.core.data.response.events.*
import com.newton.core.domain.models.adminModels.*

fun EventDto.toDomainEvent(): EventsData {
    return EventsData(
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

fun List<EventDto>.toDomainEvents(): List<EventsData> = map { it.toDomainEvent() }

fun List<EventRegistrationResponseDto>.toDomainUserTickets(): List<RegistrationResponse> =
    map { it.toEventRegistration() }
