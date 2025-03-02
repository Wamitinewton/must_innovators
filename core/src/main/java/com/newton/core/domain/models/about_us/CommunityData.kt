package com.newton.core.domain.models.about_us

import kotlinx.serialization.Serializable

@Serializable
data class Community(
    val id: Int,
    val name: String,
    val communityLead: String,
    val coLead: String,
    val secretary: String,
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