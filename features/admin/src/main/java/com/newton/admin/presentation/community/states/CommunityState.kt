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
package com.newton.admin.presentation.community.states

import com.newton.core.domain.models.admin.*

data class CommunityState(
    val sessions: List<Session> = emptyList(),
    val name: String = "",
    val lead: String = "",
    val leadId: Int? = null,
    val coLead: String = "",
    val coLeadId: Int? = null,
    val secretary: String = "",
    val secretaryId: Int? = null,
    val email: String = "",
    val phone: String = "",
    val description: String = "",
    var dateFounded: String = "",
    val toolsText: String = "",
    val dateForField: String = "",
    val currentRoleSelection: String = "",
    val sessionDate: String = "",
    val isRecruiting: Boolean = false,
    val uploadError: String? = null,
    val isLoading: Boolean = false,
    val showDatePicker: Boolean = false,
    val showBottomSheet: Boolean = false,
    val showAddSocialDialog: Boolean = false,
    val showAddSessionDialog: Boolean = false,
    val errors: Map<String, String> = emptyMap(),
    val socials: List<Socials> = emptyList(),
    val socialToEdit: Socials? = null,
    val sessionToEdit: Session? = null
)
