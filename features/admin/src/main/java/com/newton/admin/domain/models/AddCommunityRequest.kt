package com.newton.admin.domain.models

data class AddCommunityRequest(
    val name: String,
    val lead: String,
    val coLead: String,
    val secretary: String,
    val email: String,
    val phone: String,
    val isRecruiting: Boolean? = false,
    val description: String,
    val dateFounded: String,
    val tools: List<String>,
    val sessions: List<Session>,
    val socials: List<Socials>

)