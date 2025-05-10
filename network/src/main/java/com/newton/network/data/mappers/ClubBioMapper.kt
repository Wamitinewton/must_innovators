package com.newton.network.data.mappers

import com.newton.network.data.dto.aboutUs.ClubBioData
import com.newton.network.domain.models.aboutUs.ClubDetails

fun ClubBioData.toDomain(): ClubDetails {
    return ClubDetails(
        aboutUs = about_us,
        id = id,
        mission = mission,
        name = name,
        socialMedia = social_media,
        vision = vision
    )
}
