package com.newton.admin.presentation.role_management.executives.states

import com.newton.admin.data.mappers.User
import com.newton.core.enums.ExecutivePosition

data class ExecutiveState(
    val isLoading: Boolean = false,
    val isSearching:Boolean =false,
    val showBottomSheet:Boolean =false,
    val showPosition:Boolean =false,
    val expanded:Boolean =false,
    val position: ExecutivePosition?=null,
    val bio: String="",
    val selectedUser: User?= null,
    val errorMessage: String?=null,
    val successMessage:String?=null,
    val errors:Map<String,String> = emptyMap()
)
