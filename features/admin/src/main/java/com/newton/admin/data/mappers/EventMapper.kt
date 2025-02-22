package com.newton.admin.data.mappers

import com.newton.core.data.dto.EventDto
import com.newton.core.domain.models.event_models.EventsData
import com.newton.database.entities.EventsDaoEntity

object EventMapper {
    fun EventDto.toEventData(): EventsData {
        return EventsData(
            id = this.id,
            imageUrl = this.image_url,
            name = this.name,
            category = this.category,
            title = this.title,
            description = this.description,
            date = this.date,
            location = this.location,
            organizer = this.organizer,
            contactEmail = this.contact_email,
            isVirtual = this.is_virtual
        )
    }
    fun EventDto.toEventDaoEntity():EventsDaoEntity{
        return EventsDaoEntity(
            id = this.id,
            imageUrl = this.image_url?:"",
            name = this.name,
            category = this.category,
            title = this.category,
            description = this.description,
            date = this.date,
            location = this.location,
            organizer =this.organizer,
            contactEmail = this.contact_email,
            isVirtual = this.is_virtual
        )
    }
}