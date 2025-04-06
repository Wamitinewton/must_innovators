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
package com.newton.admin.data.mappers

import com.newton.database.entities.*
import com.newton.network.data.response.events.*
import com.newton.network.domain.models.adminModels.*

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
            isVirtual = this.is_virtual
        )
    }

    fun EventDto.toEventDaoEntity(): EventEntity {
        return EventEntity(
            id = this.id,
            imageUrl = this.image_url,
            name = this.name,
            category = this.category,
            description = this.description,
            date = this.date,
            location = this.location,
            organizer = this.organizer,
            contactEmail = this.contact_email,
            isVirtual = this.is_virtual
        )
    }
}
