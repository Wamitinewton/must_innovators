package com.newton.core.domain.models.admin_models

import com.newton.core.domain.models.admin.Socials

data class Club(
    val name: String,
    val aboutUs: String,
    val vision: String,
    val mission: String,
    val socialMedia: List<Socials>
)