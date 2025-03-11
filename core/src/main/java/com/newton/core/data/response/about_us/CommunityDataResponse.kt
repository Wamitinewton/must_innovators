package com.newton.core.data.response.about_us

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class CommunityApiResponse<T>(
    val message: String,
    val status: String,
    val data: T
)

@Serializable
data class CommunitiesResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<CommunityResponse>
)

@Serializable
data class CommunityResponse(
    val id: Int,
    val name: String,
    val community_lead: String,
    val co_lead: String,
    val secretary: String,
    val email: String,
    val phone_number: String,
    val description: String,
    val founding_date: String,
    val is_recruiting: Boolean,
    val social_media: List<SocialMediaResponse>,
    @Contextual val tech_stack: Any,
    val members: List<MemberResponse>,
    val total_members: Int,
    val sessions: List<SessionResponse>
)

@Serializable
data class SocialMediaResponse(
    val id: Int,
    val platform: String,
    val url: String
)

@Serializable
data class MemberResponse(
    val id: Int,
    val name: String,
    val email: String,
    val joined_at: String
)

@Serializable
data class SessionResponse(
    val day: String,
    val start_time: String,
    val end_time: String,
    val meeting_type: String,
    val location: String
)