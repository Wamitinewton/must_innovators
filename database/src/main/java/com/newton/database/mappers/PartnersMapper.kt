package com.newton.database.mappers

import com.newton.core.data.response.admin.*
import com.newton.database.entities.*

fun PartnersResponse.toPartnersEntity(): PartnerEntity {
    return PartnerEntity(
        id,
        name,
        type,
        description,
        logo,
        web_url,
        contact_email,
        contact_person,
        linked_in,
        twitter,
        start_date,
        end_date,
        ongoing,
        status,
        scope,
        benefits,
        events_supported,
        resources,
        achievements,
        target_audience
    )
}
