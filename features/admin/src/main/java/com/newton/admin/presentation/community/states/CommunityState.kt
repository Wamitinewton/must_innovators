package com.newton.admin.presentation.community.states

import com.newton.core.domain.models.admin.Session
import com.newton.core.domain.models.admin.Socials

data class CommunityState(
    val sessions: List<Session> = emptyList(),
    val name:String="",
    val lead :String="",
    val leadId :Int?=null,
    val coLead :String="",
    val coLeadId :Int?=null,
    val secretary :String="",
    val secretaryId :Int?=null,
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
    val showDatePicker: Boolean = false,
    val showBottomSheet: Boolean = false,
    val showAddSocialDialog: Boolean = false,
    val showAddSessionDialog: Boolean = false,
    val errors:Map<String,String> = emptyMap(),

    val socials: List<Socials> = emptyList(),
    val socialToEdit: Socials? = null,
    val sessionToEdit: Session? = null,

)