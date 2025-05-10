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
package com.newton.network.domain.models.aboutUs

import kotlinx.serialization.*

@Serializable
data class Community(
    val id: Int,
    val name: String,
    val communityLead: CommunityLeads?,
    val coLead: CommunityLeads?,
    val secretary: CommunityLeads?,
    val email: String,
    val phoneNumber: String,
    val description: String,
    val foundingDate: String,
    val isRecruiting: Boolean,
    val socialMedia: List<SocialMedia>,
    val techStack: List<String>,
    val members: List<Member>,
    val totalMembers: Int,
    val sessions: List<Session>
)

@Serializable
data class CommunityLeads(
    val id: Int,
    val username: String,
    val email: String,
    val position: String,
    val bio: String?
)

@Serializable
data class SocialMedia(
    val id: Int,
    val platform: String,
    val url: String
)

@Serializable
data class Member(
    val id: Int,
    val name: String,
    val email: String,
    val joinedAt: String
)

@Serializable
data class Session(
    val day: String,
    val startTime: String,
    val endTime: String,
    val meetingType: String,
    val location: String
)
