package com.newton.core.domain.models.admin_models

import com.newton.core.enums.PartnerType
import com.newton.core.enums.PartnershipStatus
import java.time.LocalDate


data class Partners(
    val name:String,
    val type:PartnerType,
    val description:String,
    val logo:String,
    val webUrl:String,
    val contactEmail:String,
    val contactPerson:String,
    val linkedIn:String,
    val twitter:String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val ongoing:Boolean,
    val status: PartnershipStatus,
    val scope:String, // what is the scope of collaboration eg mentorship
    val benefits:String,
    val eventsSupported:String,
    val resources:String,
    val achievements:String,
    val targetAudience:String //ml,Android,computer studies, engineering(eg IEEE)
)
