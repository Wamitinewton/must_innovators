/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.admin.presentation.partners.states

import com.newton.admin.data.mappers.*
import java.io.*
import java.time.*

data class AddPartnersState(
    val partnerName: String = "",
    val partnerType: String = "",
    val description: String = "",
    val website: String = "",
    val contactEmail: String = "",
    val contactPerson: String = "",
    val socialLinkedIn: String = "",
    val socialTwitter: String = "",
    val partnershipStartDate: LocalDate = LocalDate.now(),
    val partnershipEndDate: LocalDate? = null,
    val collaborationScope: String = "",
    val keyBenefits: String = "",
    val eventsSupported: String = "",
    val resourcesProvided: String = "",
    val achievements: String = "",
    val targetAudience: String = "",
    val status: String = "ACTIVE",
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val ongoingPartnership: Boolean = false,
    val isSuccess: Boolean = false,
    val partnerTypeExpanded: Boolean = false,
    val statusExpanded: Boolean = false,
    val showStartDatePicker: Boolean = false,
    val showEndDatePicker: Boolean = false,
    val errorMessage: String? = null,
    val partnershipLogo: File? = null,
    val errors: Map<String, String> = emptyMap()
)

sealed class AddPartnersEffect {
    data object PickImage : AddPartnersEffect()
}
