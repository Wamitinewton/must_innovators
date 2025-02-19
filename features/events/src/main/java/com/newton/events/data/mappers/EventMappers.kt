package com.newton.events.data.mappers

import com.newton.core.data.dto.EventDto
import com.newton.core.data.dto.EventRegistrationResponseDto
import com.newton.core.domain.models.event_models.EventsData
import com.newton.core.domain.models.event_models.RegistrationResponse
import com.newton.database.entities.EventEntity


object EventMappers {
    fun EventDto.toDomainEvent(): EventsData {
        return EventsData(
            id = id,
            imageUrl = image_url,
            name = name,
            category = category,
            title = title,
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

    fun EventEntity.toDomainEvent() = EventsData(
        id = id,
        imageUrl = imageUrl,
        name = name,
        category = category,
        title = title,
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
        title = title,
        description = description,
        date = date,
        location = location,
        organizer = organizer,
        contactEmail = contactEmail,
        isVirtual = isVirtual,
        pageNumber = pageNumber
    )

}


