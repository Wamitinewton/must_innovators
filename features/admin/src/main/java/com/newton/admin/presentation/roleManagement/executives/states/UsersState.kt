package com.newton.admin.presentation.roleManagement.executives.states

import com.newton.admin.data.mappers.*

data class ExecutiveUsersState(
    val getUsersError: String? = null,
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val searchQuery: String = ""
)
