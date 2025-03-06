package com.newton.core.domain.models.admin_models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddCommunityResponse(
    @SerialName("data")
    val `data`: CommunityData = CommunityData(),
    @SerialName("message")
    val message: String = "",
    @SerialName("status")
    val status: String = ""
)

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
    val techStack: String = "",
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