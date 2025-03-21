package com.newton.core.domain.models.about_us

import kotlinx.serialization.Serializable

@Serializable
data class ClubBio(
    val data: ClubBioData,
    val message: String,
    val status: String
)

@Serializable
data class ClubBioData(
    val about_us: String,
    val id: Int,
    val mission: String,
    val name: String,
    val social_media: List<SocialMediaX>,
    val vision: String
)

@Serializable
data class SocialMediaX(
    val platform: String,
    val url: String
)

