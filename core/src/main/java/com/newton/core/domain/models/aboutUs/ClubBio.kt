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
package com.newton.core.domain.models.aboutUs

import kotlinx.serialization.*

@Serializable
data class ClubBio(
    val data: ClubBioData,
    val message: String,
    val status: String
)

@Serializable
data class ClubBioData(
    val about_us: String,
    val id: Int,
    val mission: String,
    val name: String,
    val social_media: List<SocialMediaX>,
    val vision: String
)

@Serializable
data class SocialMediaX(
    val platform: String,
    val url: String
)
