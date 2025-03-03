package com.newton.admin.presentation.community.events

import com.newton.admin.domain.models.Session
import com.newton.admin.domain.models.Socials

sealed class CommunityEvent {
    data class SessionsChanged(val session: List<Session>): CommunityEvent()
    data class NameChanged(val name: String): CommunityEvent()
    data class LeadChanged(val lead: String): CommunityEvent()
    data class CoLeadChanged(val coLead:String): CommunityEvent()
    data class EmailCHanged(val email: String): CommunityEvent()
    data class SecretaryChanged(val secretary: String): CommunityEvent()
    data class PhoneChanged(val phone: String): CommunityEvent()
    data class SocialsChanged(val socials: List<Socials>): CommunityEvent()
    data class DescriptionChanged(val description: String): CommunityEvent()
    data class DateFoundedChanged(val date: String): CommunityEvent()
    data class ToolsChanged(val tools: String): CommunityEvent()
    data class IsRecruitingChanged(val recruiting: Boolean): CommunityEvent()
    data object AddCommunity : CommunityEvent()
}