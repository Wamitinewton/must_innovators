package com.newton.core.domain.models.auth_models

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val first_name: String? = null,
    val last_name: String? = null,
    val email: String? = null,
    val course: String? = null,
    val registration_no: String? = null,
    val bio: String? = null,
    val tech_stacks: List<String>? = null,
    val social_media: SocialMedia? = null,
    val year_of_study: Int? = null,
    val graduation_year: Int? = null,
    val projects: List<Project>? = null,
    val skills: List<String>? = null
)