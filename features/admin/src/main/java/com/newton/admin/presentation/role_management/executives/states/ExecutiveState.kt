package com.newton.admin.presentation.role_management.executives.states

import com.newton.admin.presentation.role_management.executives.view.Community
import com.newton.admin.presentation.role_management.executives.view.Executive
import com.newton.admin.presentation.role_management.executives.view.User

data class ExecutiveState(
    val isLoading: Boolean = false,
    val isSearching:Boolean =false,
    val communities: List<Community> = emptyList(),
    val selectedCommunity: Community? = null,
    val selectedUser: User?= null,
    val errorMessage: String?=null,
    val successMessage:String?=null,
    val executiveState: Executive?=null
)
