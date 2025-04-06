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

import com.newton.database.entities.*
import com.newton.network.domain.models.homeModels.*

fun PartnersData.toPartnerEntity(): PartnersDataEntity {
    return PartnersDataEntity(
        id = id,
        achievements = achievements,
        benefits = benefits,
        contact_email = contact_email,
        contact_person = contact_person,
        description = description,
        end_date = end_date,
        events_supported = events_supported,
        linked_in = linked_in,
        logo = logo,
        name = name,
        ongoing = ongoing,
        resources = resources,
        scope = scope,
        start_date = start_date,
        status = status,
        target_audience = target_audience,
        twitter = twitter,
        type = type,
        web_url = web_url
    )
}

fun PartnersDataEntity.toDomainPartners(): PartnersData {
    return PartnersData(
        id = id,
        achievements = achievements,
        benefits = benefits,
        contact_email = contact_email,
        contact_person = contact_person,
        description = description,
        end_date = end_date,
        events_supported = events_supported,
        linked_in = linked_in,
        logo = logo,
        name = name,
        ongoing = ongoing,
        resources = resources,
        scope = scope,
        start_date = start_date,
        status = status,
        target_audience = target_audience,
        twitter = twitter,
        type = type,
        web_url = web_url
    )
}
