package com.newton.core.domain.models.homeModels

data class AboutUs(
    val communityName: String,
    val aboutText: String,
    val mission: String,
    val vision: String,
    val socials: List<SocialLink>
)
