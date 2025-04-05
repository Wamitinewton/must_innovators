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
package com.newton.core.domain.models.adminModels

import kotlinx.serialization.*

@Serializable
data class CommunityData(
    @SerialName("co_lead")
    val coLead: String = "",
    @SerialName("community_lead")
    val communityLead: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("email")
    val email: String = "",
    @SerialName("founding_date")
    val foundingDate: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("is_recruiting")
    val isRecruiting: Boolean = false,
    @SerialName("members")
    val members: List<MemberResponse> = listOf(),
    @SerialName("name")
    val name: String = "",
    @SerialName("phone_number")
    val phoneNumber: String = "",
    @SerialName("secretary")
    val secretary: String = "",
    @SerialName("sessions")
    val sessions: List<Session> = listOf(),
    @SerialName("social_media")
    val socialMedia: List<SocialMedia> = listOf(),
    @SerialName("tech_stack")
    val techStack: List<String> = emptyList(),
    @SerialName("total_members")
    val totalMembers: Int = 0
)

@Serializable
data class Session(
    @SerialName("day")
    val day: String = "",
    @SerialName("end_time")
    val endTime: String = "",
    @SerialName("location")
    val location: String = "",
    @SerialName("meeting_type")
    val meetingType: String = "",
    @SerialName("start_time")
    val startTime: String = ""
)

@Serializable
data class SocialMedia(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("platform")
    val platform: String = "",
    @SerialName("url")
    val url: String = ""
)

@Serializable
data class MemberResponse(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    @SerialName("joined_at")
    val joinedAt: String = ""
)
