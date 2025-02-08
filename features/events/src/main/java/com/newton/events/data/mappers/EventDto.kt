package com.newton.events.data.mappers

import com.newton.database.entities.EventEntity
import com.newton.events.data.remote.response.EventData
import com.newton.events.data.remote.response.EventResponse
import com.newton.events.domain.models.Event

fun EventData.toDomainEvent(): Event{
    return Event(
        id = id,
        contactEmail = contactEmail,
        date = date,
        description = description,
        image = image,
        isVirtual = isVirtual,
        location = location,
        name = name,
        organizer = organizer,
        title = title,
    )
}

fun EventEntity.toEventModel(): Event{
    return Event(
        id = id,
        contactEmail = contactEmail,
        date = date,
        description = description,
        image = image,
        isVirtual = isVirtual,
        location = location,
        name = name,
        organizer = organizer,
        title = title,
    )
}

fun EventData.toEventEntity(): EventEntity{
    return EventEntity(
        id = id,
        contactEmail = contactEmail,
        date = date,
        description = description,
        image = image,
        isVirtual = isVirtual,
        location = location,
        name = name,
        organizer = organizer,
        title = title,
    )
}

fun List<EventData>.toListOfEvent(): List<Event> = map { it.toDomainEvent() }

fun List<EventEntity>.toEventList():List<Event> = map{it.toEventModel()}
