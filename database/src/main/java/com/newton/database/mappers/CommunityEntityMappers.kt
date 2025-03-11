package com.newton.database.mappers

import com.newton.core.domain.models.about_us.Community
import com.newton.core.domain.models.about_us.Member
import com.newton.core.domain.models.about_us.Session
import com.newton.core.domain.models.about_us.SocialMedia
import com.newton.database.entities.CommunityEntity
import com.newton.database.entities.CommunityWithRelations
import com.newton.database.entities.MemberEntity
import com.newton.database.entities.SessionEntity
import com.newton.database.entities.SocialMediaEntity
import com.newton.database.entities.TechStackEntity

fun Community.toEntity(): CommunityEntity {
    return CommunityEntity(
        id = id,
        name = name,
        communityLead = communityLead,
        coLead = coLead,
        secretary = secretary,
        email = email,
        phoneNumber = phoneNumber,
        description = description,
        foundingDate = foundingDate,
        isRecruiting = isRecruiting,
        totalMembers = totalMembers
    )
}

fun SocialMedia.toEntity(communityId: Int): SocialMediaEntity {
    return SocialMediaEntity(
        id = id,
        communityId = communityId,
        platform = platform,
        url = url
    )
}

fun Member.toEntity(communityId: Int): MemberEntity {
    return MemberEntity(
        id = id,
        communityId = communityId,
        name = name,
        email = email,
        joinedAt = joinedAt
    )
}

fun Session.toEntity(communityId: Int): SessionEntity {
    return SessionEntity(
        communityId = communityId,
        day = day,
        startTime = startTime,
        endTime = endTime,
        meetingType = meetingType,
        location = location
    )
}

fun String.toTechStackEntity(communityId: Int): TechStackEntity {
    return TechStackEntity(
        communityId = communityId,
        technology = this
    )
}

fun CommunityWithRelations.toDomain(): Community {
    return Community(
        id = communityEntity.id,
        name = communityEntity.name,
        communityLead = communityEntity.communityLead,
        coLead = communityEntity.coLead,
        secretary = communityEntity.secretary,
        email = communityEntity.email,
        phoneNumber = communityEntity.phoneNumber,
        description = communityEntity.description,
        foundingDate = communityEntity.foundingDate,
        isRecruiting = communityEntity.isRecruiting,
        socialMedia = socialMedia.map { it.toDomain() },
        techStack = techStack.map { it.technology },
        members = members.map { it.toDomain() },
        totalMembers = communityEntity.totalMembers,
        sessions = sessions.map { it.toDomain() }
    )
}

fun SocialMediaEntity.toDomain(): SocialMedia {
    return SocialMedia(
        id = id,
        platform = platform,
        url = url
    )
}

fun MemberEntity.toDomain(): Member {
    return Member(
        id = id,
        name = name,
        email = email,
        joinedAt = joinedAt
    )
}

fun SessionEntity.toDomain(): Session {
    return Session(
        day = day,
        startTime = startTime,
        endTime = endTime,
        meetingType = meetingType,
        location = location
    )
}