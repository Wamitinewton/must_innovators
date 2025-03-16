package com.newton.database.mappers

import com.newton.core.domain.models.home_models.PartnersData
import com.newton.database.entities.PartnersDataEntity

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