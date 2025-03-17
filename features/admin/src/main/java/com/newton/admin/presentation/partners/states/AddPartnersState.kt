package com.newton.admin.presentation.partners.states

import com.newton.admin.data.mappers.User
import java.io.File
import java.time.LocalDate

data class AddPartnersState (
    val partnerName:String = "",
    val partnerType:String = "",
    val description:String = "",
    val website:String = "",
    val contactEmail:String = "",
    val contactPerson:String = "",
    val socialLinkedIn:String = "",
    val socialTwitter:String = "",
    val partnershipStartDate:LocalDate = LocalDate.now(),
    val partnershipEndDate:LocalDate? = null,
    val collaborationScope:String = "",
    val keyBenefits:String = "",
    val eventsSupported:String = "",
    val resourcesProvided:String = "",
    val achievements:String = "",
    val targetAudience:String = "",
    val status:String = "ACTIVE",
    val users:List<User> = emptyList(),
    val isLoading:Boolean = false,
    val ongoingPartnership:Boolean = false,
    val isSuccess:Boolean = false,
    val partnerTypeExpanded:Boolean = false,
    val statusExpanded:Boolean = false,
    val showStartDatePicker:Boolean = false,
    val showEndDatePicker:Boolean = false,
    val errorMessage:String? = null,
    val partnershipLogo: File? = null,
    val errors: Map<String, String> = emptyMap(),
)

sealed class AddPartnersEffect {
    data object PickImage : AddPartnersEffect()
}
