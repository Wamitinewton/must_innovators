package com.newton.admin.data.mappers

import com.newton.core.data.response.events.EventDto
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.domain.models.admin_models.FeedbackData
import com.newton.core.domain.models.admin_models.UserFeedbackResponse
import com.newton.database.entities.EventEntity

object EventMapper {
    fun EventDto.toEventData(): EventsData {
        return EventsData(
            id = this.id,
            imageUrl = this.image_url,
            name = this.name,
            category = this.category,
            description = this.description,
            date = this.date,
            location = this.location,
            organizer = this.organizer,
            contactEmail = this.contact_email,
            isVirtual = this.is_virtual,
        )
    }
    fun EventDto.toEventDaoEntity():EventEntity{
        return EventEntity(
            id = this.id,
            imageUrl = this.image_url,
            name = this.name,
            category = this.category,
            description = this.description,
            date = this.date,
            location = this.location,
            organizer =this.organizer,
            contactEmail = this.contact_email,
            isVirtual = this.is_virtual
        )
    }
}