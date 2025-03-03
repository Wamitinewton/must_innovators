package com.newton.admin.presentation.community.states

import com.newton.admin.domain.models.Session
import com.newton.admin.domain.models.Socials

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