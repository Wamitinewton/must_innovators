package com.newton.core.domain.models.admin_models

import com.newton.core.enums.PartnerType
import com.newton.core.enums.PartnershipStatus
import java.io.File


data class AddPartnerRequest(
    val name:String,
    val type:String,
    val description:String,
    val logo: File,
    val webUrl:String,
    val contactEmail:String,
    val contactPerson:String,
    val linkedIn:String,
    val twitter:String,
    val startDate: String,
    val endDate: String,
    val ongoing:Boolean,
    val status: String,
    val scope:String, // what is the scope of collaboration eg mentorship
    val benefits:String,
    val eventsSupported:String,
    val resources:String,
    val achievements:String,
    val targetAudience:String //ml,Android,computer studies, engineering(eg IEEE)
)
