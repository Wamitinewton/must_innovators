package com.newton.core.domain.models.homeModels

import kotlinx.serialization.*

@Serializable
data class Partners(
    val count: Int,
    val next: String?,
    val previous: String,
    val results: List<PartnersData>
)

@Serializable
data class PartnersData(
    val achievements: String,
    val benefits: String,
    val contact_email: String,
    val contact_person: String,
    val description: String,
    val end_date: String?,
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
