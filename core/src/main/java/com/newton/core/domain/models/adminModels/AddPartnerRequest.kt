package com.newton.core.domain.models.adminModels

import java.io.*

data class AddPartnerRequest(
    val name: String,
    val type: String,
    val description: String,
    val logo: File,
    val webUrl: String,
    val contactEmail: String,
    val contactPerson: String,
    val linkedIn: String,
    val twitter: String,
    val startDate: String,
    val endDate: String? = null,
    val ongoing: Boolean,
    val status: String,
    val scope: String,
    val benefits: String,
    val eventsSupported: String,
    val resources: String,
    val achievements: String,
    val targetAudience: String
)
