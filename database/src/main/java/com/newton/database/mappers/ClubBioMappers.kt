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

import com.newton.network.domain.models.aboutUs.*
import com.newton.database.entities.*

fun ClubBioEntity.toDomain(): ClubBioData {
    return ClubBioData(
        id = id,
        name = name,
        about_us = aboutUs,
        mission = mission,
        vision = vision,
        social_media =
        socialMedia.map {
            com.newton.network.domain.models.aboutUs.SocialMediaX(
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
        socialMedia =
        social_media.map {
            ClubSocialMediaEntity(
                platform = it.platform,
                url = it.url
            )
        }
    )
}
