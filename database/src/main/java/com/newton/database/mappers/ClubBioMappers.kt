package com.newton.database.mappers

import com.newton.core.domain.models.about_us.ClubBioData
import com.newton.core.domain.models.about_us.SocialMediaX
import com.newton.database.entities.ClubBioEntity
import com.newton.database.entities.ClubSocialMediaEntity

fun ClubBioEntity.toDomain(): ClubBioData {
    return ClubBioData(
        id = id,
        name = name,
        about_us = aboutUs,
        mission = mission,
        vision = vision,
        social_media = socialMedia.map {
            SocialMediaX(
                platform = it.platform,
                url = it.url
            )
        }
    )
}

fun ClubBioData.toEntity(): ClubBioEntity {
    return ClubBioEntity(
        id = id,
        name = name,
        aboutUs = about_us,
        mission = mission,
        vision = vision,
        socialMedia = social_media.map {
            ClubSocialMediaEntity(
                platform = it.platform,
                url = it.url
            )
        }
    )
}