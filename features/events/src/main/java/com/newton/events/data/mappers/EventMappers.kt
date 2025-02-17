package com.newton.events.data.mappers

import com.newton.core.data.dto.EventDto
import com.newton.core.domain.models.event_models.EventsData


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

}


