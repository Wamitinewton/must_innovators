package com.newton.admin.presentation.community.states

import com.newton.core.domain.models.admin.Session
import com.newton.core.domain.models.admin.Socials

data class CommunityState(
    val sessions: List<Session> = emptyList(),
    val name:String="",
    var lead :String="",
    var coLead :String="",
    var secretary :String="",
    val email :String="",
    val phone :String="",
    val socials: List<Socials> = emptyList(),
    val description :String="",
    var dateFounded :String="",
    val toolsText :String="",
    val isRecruiting: Boolean? = false,
    val uploadError: String? = null,
    val isLoading: Boolean = false,
    val uploadSuccess: Boolean = false
)