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
package com.newton.network.data.mappers

import com.newton.network.data.dto.aboutUs.*
import com.newton.network.domain.models.admin.Session as AdminSession

fun CommunityResponse.toDomain(): com.newton.network.domain.models.aboutUs.Community {
    return com.newton.network.domain.models.aboutUs.Community(
        id = id,
        name = name,
        communityLead = community_lead_details?.toDomain(),
        coLead = co_lead_details?.toDomain(),
        secretary = secretary_details?.toDomain(),
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

fun CommunityLeadsResponse.toDomain(): com.newton.network.domain.models.aboutUs.CommunityLeads {
    return com.newton.network.domain.models.aboutUs.CommunityLeads(
        id = id,
        username = username,
        email = email,
        position = first_name,
        bio = last_name
    )
}

fun SocialMediaResponse.toDomain(): com.newton.network.domain.models.aboutUs.SocialMedia {
    return com.newton.network.domain.models.aboutUs.SocialMedia(
        id = id,
        platform = platform,
        url = url
    )
}

fun MemberResponse.toDomain(): com.newton.network.domain.models.aboutUs.Member {
    return com.newton.network.domain.models.aboutUs.Member(
        id = id,
        name = name,
        email = email,
        joinedAt = joined_date
    )
}

fun SessionResponse.toDomain(): com.newton.network.domain.models.aboutUs.Session {
    return com.newton.network.domain.models.aboutUs.Session(
        day = day,
        startTime = start_time,
        endTime = end_time,
        meetingType = meeting_type,
        location = location
    )
}

fun CommunitiesResponse.toDomainList(): List<com.newton.network.domain.models.aboutUs.Community> = results.map { it.toDomain() }

private fun parseTechStack(techStack: Any): List<String> {
    return when (techStack) {
        is List<*> -> techStack.filterIsInstance<String>()
        is String -> techStack.split(",").map { it.trim() }
        else -> emptyList()
    }
}

fun com.newton.network.domain.models.aboutUs.Session.toAdminSession(): AdminSession {
    return AdminSession(
        day = day,
        end_time = endTime,
        location = location,
        meeting_type = meetingType,
        start_time = startTime
    )
}

fun AdminSession.toAboutUs(): com.newton.network.domain.models.aboutUs.Session {
    return com.newton.network.domain.models.aboutUs.Session(
        day = day,
        startTime = start_time,
        endTime = end_time,
        meetingType = meeting_type,
        location = location
    )
}
