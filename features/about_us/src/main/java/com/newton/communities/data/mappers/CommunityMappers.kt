package com.newton.communities.data.mappers

import com.newton.communities.data.response.CommunitiesResponse
import com.newton.communities.data.response.CommunityResponse
import com.newton.communities.data.response.MemberResponse
import com.newton.communities.data.response.SessionResponse
import com.newton.communities.data.response.SocialMediaResponse
import com.newton.core.domain.models.about_us.Community
import com.newton.core.domain.models.about_us.Member
import com.newton.core.domain.models.about_us.Session
import com.newton.core.domain.models.about_us.SocialMedia

fun CommunityResponse.toDomain(): Community {
    return Community(
        id = id,
        name = name,
        communityLead = community_lead,
        coLead = co_lead,
        secretary = secretary,
        email = email,
        phoneNumber = phone_number,
        description = description,
        foundingDate = founding_date,
        isRecruiting = is_recruiting,
        socialMedia = social_media.map { it.toDomain() },
        techStack = parseTechStack(tech_stack),
        members = members.map { it.toDomain() },
        totalMembers = total_members,
        sessions = sessions.map { it.toDomain() }
    )
}

fun SocialMediaResponse.toDomain(): SocialMedia {
    return SocialMedia(
        id = id,
        platform = platform,
        url = url
    )
}

fun MemberResponse.toDomain(): Member {
    return Member(
        id = id,
        name = name,
        email = email,
        joinedAt = joined_at
    )
}

fun SessionResponse.toDomain(): Session {
    return Session(
        day = day,
        startTime = start_time,
        endTime = end_time,
        meetingType = meeting_type,
        location = location
    )
}

fun CommunitiesResponse.toDomainList(): List<Community> {
    return results.map { it.toDomain() }
}

private fun parseTechStack(techStack: Any): List<String> {
    return when (techStack) {
        is List<*> -> techStack.filterIsInstance<String>()
        is String -> techStack.split(",").map { it.trim() }
        else -> emptyList()
    }
}