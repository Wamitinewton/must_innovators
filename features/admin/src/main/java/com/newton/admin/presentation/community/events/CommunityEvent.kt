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

import com.newton.core.domain.models.admin.*

sealed class CommunityEvent {
    data class SessionsChanged(val session: List<Session>) : CommunityEvent()

    data class NameChanged(val name: String) : CommunityEvent()

    data class LeadChanged(val lead: String) : CommunityEvent()

    data class SearchQuery(val query: String) : CommunityEvent()

    data class LeadIdChanged(val leadId: Int) : CommunityEvent()

    data class CoLeadChanged(val coLead: String) : CommunityEvent()

    data class CoLeadIdChanged(val coLeadId: Int) : CommunityEvent()

    data class EmailCHanged(val email: String) : CommunityEvent()

    data class SecretaryChanged(val secretary: String) : CommunityEvent()

    data class SecretaryIdChanged(val secretaryId: Int) : CommunityEvent()

    data class PhoneChanged(val phone: String) : CommunityEvent()

    data class SocialsChanged(val socials: List<Socials>) : CommunityEvent()

    data class DescriptionChanged(val description: String) : CommunityEvent()

    data class DateForFieldChange(val dateForField: String) : CommunityEvent()

    data class DateFoundedChanged(val date: String) : CommunityEvent()

    data class ToolsChanged(val tools: String) : CommunityEvent()

    data class SessionDate(val date: String) : CommunityEvent()

    data class DateFounded(val date: String) : CommunityEvent()

    data class CurrentRoleSelectionChange(val currentRole: String) : CommunityEvent()

    data class IsRecruitingChanged(val recruiting: Boolean) : CommunityEvent()

    data class ShowDatePicker(val shown: Boolean) : CommunityEvent()

    data class ShowBottomSheet(val shown: Boolean) : CommunityEvent()

    data class ShowAddSocialDialog(val shown: Boolean) : CommunityEvent()

    data class ShowAddSession(val shown: Boolean) : CommunityEvent()

    data class SocialToEditChange(val social: Socials?) : CommunityEvent()

    data class SessionToEdit(val session: Session?) : CommunityEvent()

    data object AddCommunity : CommunityEvent()

    data class LoadUsers(val isRefresh: Boolean) : CommunityEvent()
}
