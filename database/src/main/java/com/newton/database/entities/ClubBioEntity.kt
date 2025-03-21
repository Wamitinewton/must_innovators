package com.newton.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "club_bio")
data class ClubBioEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val aboutUs: String,
    val mission: String,
    val vision: String,
    val socialMedia: List<ClubSocialMediaEntity>
)

data class ClubSocialMediaEntity(
    val platform: String,
    val url: String
)

