package com.newton.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "communities")
data class CommunityEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val communityLead: String,
    val coLead: String,
    val secretary: String,
    val email: String,
    val phoneNumber: String,
    val description: String,
    val foundingDate: String,
    val isRecruiting: Boolean,
    val totalMembers: Int
)


@Entity(tableName = "social_media")
data class SocialMediaEntity(
    @PrimaryKey val id: Int,
    val communityId: Int,
    val platform: String,
    val url: String
)

@Entity(tableName = "tech_stack")
data class TechStackEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val communityId: Int,
    val technology: String
)

@Entity(tableName = "members")
data class MemberEntity(
    @PrimaryKey val id: Int,
    val communityId: Int,
    val name: String,
    val email: String,
    val joinedAt: String
)

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val communityId: Int,
    val day: String,
    val startTime: String,
    val endTime: String,
    val meetingType: String,
    val location: String
)

data class CommunityWithRelations(

    @Embedded val communityEntity: CommunityEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "communityId"
    )
    val socialMedia: List<SocialMediaEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "communityId"
    )
    val techStack: List<TechStackEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "communityId"
    )
    val members: List<MemberEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "communityId"
    )
    val sessions: List<SessionEntity>
)