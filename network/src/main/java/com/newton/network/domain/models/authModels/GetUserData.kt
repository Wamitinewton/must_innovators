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
package com.newton.network.domain.models.authModels

import kotlinx.serialization.*

@Serializable
data class SocialMedia(
    val github: String? = null,
    val linkedin: String? = null,
    val twitter: String? = null
)

@Serializable
data class Project(
    val name: String,
    val description: String,
    val technologies: List<String>,
    val link: String
)

@Serializable
data class UserData(
    val id: Int? = null,
    val username: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val course: String? = null,
    val registration_no: String? = null,
    val bio: String? = null,
    val tech_stacks: List<String>? = null,
    val social_media: SocialMedia? = null,
    val photo: String? = null,
    val year_of_study: Int? = null,
    val graduation_year: Int? = null,
    val projects: List<Project>? = null,
    val skills: List<String>? = null
)

@Serializable
data class GetUserData(
    val data: UserData,
    val message: String,
    val status: String
)
