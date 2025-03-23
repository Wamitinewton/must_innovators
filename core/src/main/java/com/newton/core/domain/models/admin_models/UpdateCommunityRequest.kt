package com.newton.core.domain.models.admin_models

import com.newton.core.domain.models.admin.Session
import com.newton.core.domain.models.admin.Socials

data class UpdateCommunityRequest(
    val name: String? = null,
    val communityLead: String?= null,
    val coLead: String?=null,
    val secretary: String?=null,
    val email: String?=null,
    val phoneNumber: String?= null,
    val isRecruiting: Boolean? = null,
    val description: String?=null,
    val foundingDate: String?=null,
    val techStack: List<String>?=null,
    val sessions: List<Session>?=null,
    val socialMedia: List<Socials>?=null

)