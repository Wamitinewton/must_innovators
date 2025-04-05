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
package com.newton.admin.presentation.community.events

import com.newton.core.domain.models.aboutUs.*
import com.newton.core.domain.models.admin.*
import com.newton.core.domain.models.admin.Session

sealed class UpdateCommunityEvent {
    data class SessionsChanged(val session: List<Session>) : UpdateCommunityEvent()

    data class NameChanged(val name: String) : UpdateCommunityEvent()

    data class LeadChanged(val lead: String) : UpdateCommunityEvent()

    data class CoLeadChanged(val coLead: String) : UpdateCommunityEvent()

    data class EmailCHanged(val email: String) : UpdateCommunityEvent()

    data class SecretaryChanged(val secretary: String) : UpdateCommunityEvent()

    data class PhoneChanged(val phone: String) : UpdateCommunityEvent()

    data class SocialsChanged(val socials: List<Socials>) : UpdateCommunityEvent()

    data class DescriptionChanged(val description: String) : UpdateCommunityEvent()

    data class DateForFieldChange(val dateForField: String) : UpdateCommunityEvent()

    data class DateFoundedChanged(val date: String) : UpdateCommunityEvent()

    data class ToolsChanged(val tools: String) : UpdateCommunityEvent()

    data class SessionDate(val date: String) : UpdateCommunityEvent()

    data class DateFounded(val date: String) : UpdateCommunityEvent()

    data class CurrentRoleSelectionChange(val currentRole: String) : UpdateCommunityEvent()

    data class IsRecruitingChanged(val recruiting: Boolean) : UpdateCommunityEvent()

    data class ShowDatePicker(val shown: Boolean) : UpdateCommunityEvent()

    data class ShowBottomSheet(val shown: Boolean) : UpdateCommunityEvent()

    data class ShowAddSocialDialog(val shown: Boolean) : UpdateCommunityEvent()

    data class ShowAddSession(val shown: Boolean) : UpdateCommunityEvent()

    data class SocialToEditChange(val social: Socials?) : UpdateCommunityEvent()

    data class SessionToEdit(val session: Session?) : UpdateCommunityEvent()

    data class UpdateCommunity(val communityId: Int) : UpdateCommunityEvent()

    data object ToDefault : UpdateCommunityEvent()

    data class LoadUsers(val isRefresh: Boolean) : UpdateCommunityEvent()

    data class IsEditingChange(val editing: Boolean) : UpdateCommunityEvent()
}

sealed class UpdateCommunityEffect {
    data object Initial : UpdateCommunityEffect()

    data class Success(val community: Community) : UpdateCommunityEffect()

    data class Error(val message: String) : UpdateCommunityEffect()
}
