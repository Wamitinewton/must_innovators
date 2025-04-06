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

import com.newton.network.domain.models.aboutUs.*
import com.newton.network.domain.models.admin.*
import com.newton.network.domain.models.admin.Session

data class UpdateCommunityState(
    val sessions: List<Session>? = null,
    val name: String? = null,
    val lead: String? = null,
    val coLead: String? = null,
    val secretary: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val description: String? = null,
    val dateFounded: String? = null,
    val toolsText: String? = null,
    val dateForField: String? = null,
    val currentRoleSelection: String? = null,
    val sessionDate: String? = null,
    val isRecruiting: Boolean = false,
    val uploadError: String? = null,
    val isLoading: Boolean = false,
    val uploadSuccess: Boolean = false,
    val showDatePicker: Boolean = false,
    val showBottomSheet: Boolean = false,
    val showAddSocialDialog: Boolean = false,
    val showAddSessionDialog: Boolean = false,
    val socials: List<Socials>? = null,
    val socialToEdit: Socials? = null,
    val sessionToEdit: Session? = null,
    val communities: List<Community> = emptyList(),
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val isEditing: Boolean = false
)
