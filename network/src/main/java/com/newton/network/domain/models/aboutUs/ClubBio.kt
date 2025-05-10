package com.newton.network.domain.models.aboutUs

import com.newton.network.data.dto.aboutUs.SocialMediaX
import kotlinx.serialization.Serializable

@Serializable
data class ClubDetails(
    val aboutUs: String,
    val id: Int,
    val mission: String,
    val name: String,
    val socialMedia: List<SocialMediaX>,
    val vision: String
)