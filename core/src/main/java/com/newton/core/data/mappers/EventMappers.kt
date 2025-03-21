package com.newton.core.data.mappers

import com.newton.core.data.response.events.EventDto
import com.newton.core.data.response.events.EventRegistrationResponseDto
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.domain.models.admin_models.RegistrationResponse


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
            uid = uid
        )
    }

    fun List<EventDto>.toDomainEvents(): List<EventsData> =
        map { it.toDomainEvent() }






