package com.newton.core.domain.models.admin

data class AddCommunityRequest(
    val name: String,
    val community_lead: String,
    val co_lead: String,
    val secretary: String,
    val email: String,
    val phone_number: String,
    val is_recruiting: Boolean? = false,
    val description: String,
    val founding_date: String,
    val tech_stack: List<String>,
    val sessions: List<Session>,
    val social_media: List<Socials>

)