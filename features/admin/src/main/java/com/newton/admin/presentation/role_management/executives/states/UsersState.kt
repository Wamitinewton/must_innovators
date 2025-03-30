package com.newton.admin.presentation.role_management.executives.states

import com.newton.admin.data.mappers.User

data class ExecutiveUsersState(
    val getUsersError:String? = null,
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val searchQuery:String = ""
)
