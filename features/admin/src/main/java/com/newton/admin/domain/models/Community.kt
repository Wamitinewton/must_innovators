package com.newton.admin.domain.models

data class Community(
    val name: String,
    val lead: String,
    val coLead: String,
    val secretary: String,
    val email: String,
    val phone: String,
    val githubLink: String,
    val linkedinLink: String,
    val description: String,
    val dateFounded: String,
    val tools: List<String>
)