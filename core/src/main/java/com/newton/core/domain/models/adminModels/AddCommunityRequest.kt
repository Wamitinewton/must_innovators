package com.newton.core.domain.models.adminModels

import com.newton.core.domain.models.admin.*
import com.newton.core.domain.models.admin.Session

data class AddCommunityRequest(
    val name: String,
    val community_lead: Int,
    val co_lead: Int,
    val secretary: Int,
    val email: String,
    val phone_number: String,
    val is_recruiting: Boolean? = false,
    val description: String,
    val founding_date: String,
    val tech_stack: List<String>,
    val sessions: List<Session>,
    val social_media: List<Socials>
)
