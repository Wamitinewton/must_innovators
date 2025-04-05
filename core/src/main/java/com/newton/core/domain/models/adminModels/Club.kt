package com.newton.core.domain.models.adminModels

import com.newton.core.domain.models.admin.*

data class Club(
    val name: String,
    val aboutUs: String,
    val vision: String,
    val mission: String,
    val socialMedia: List<Socials>
)
