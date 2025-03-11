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
    val description :String="",
    var dateFounded :String="",
    val toolsText :String="",
    val dateForField: String = "",
    val currentRoleSelection: String = "",
    val sessionDate: String = "",
    val isRecruiting: Boolean = false,
    val uploadError: String? = null,
    val isLoading: Boolean = false,
    val uploadSuccess: Boolean = false,
    val showDatePicker: Boolean = false,
    val showBottomSheet: Boolean = false,
    val showAddSocialDialog: Boolean = false,
    val showAddSessionDialog: Boolean = false,

    val socials: List<Socials> = emptyList(),
    val socialToEdit: Socials? = null,
    val sessionToEdit: Session? = null,

)