package com.newton.core.data.response.admin

data class PartnersResponse(
    val achievements: String,
    val benefits: String,
    val contact_email: String,
    val contact_person: String,
    val description: String,
    val end_date: String,
    val events_supported: String,
    val id: Int,
    val linked_in: String,
    val logo: String,
    val name: String,
    val ongoing: Boolean,
    val resources: String,
    val scope: String,
    val start_date: String,
    val status: String,
    val target_audience: String,
    val twitter: String,
    val type: String,
    val web_url: String
)