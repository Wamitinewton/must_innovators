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
package com.newton.core.data.mappers

import com.newton.core.data.response.aboutUs.*
import com.newton.core.domain.models.aboutUs.*
import com.newton.core.domain.models.aboutUs.Session
import com.newton.core.domain.models.admin.Session as AdminSession

fun CommunityResponse.toDomain(): Community {
    return Community(
        id = id,
        name = name,
        communityLead = community_lead_details.toDomain(),
        coLead = co_lead_details.toDomain(),
        secretary = secretary_details.toDomain(),
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

fun CommunityLeadsResponse.toDomain(): CommunityLeads {
    return CommunityLeads(
        id = id,
        name = name,
        email = email,
        position = position,
        bio = bio
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

fun CommunitiesResponse.toDomainList(): List<Community> = results.map { it.toDomain() }

private fun parseTechStack(techStack: Any): List<String> {
    return when (techStack) {
        is List<*> -> techStack.filterIsInstance<String>()
        is String -> techStack.split(",").map { it.trim() }
        else -> emptyList()
    }
}

fun Session.toAdminSession(): AdminSession {
    return AdminSession(
        day = day,
        end_time = endTime,
        location = location,
        meeting_type = meetingType,
        start_time = startTime
    )
}

fun AdminSession.toAboutUs(): Session {
    return Session(
        day = day,
        startTime = start_time,
        endTime = end_time,
        meetingType = meeting_type,
        location = location
    )
}
