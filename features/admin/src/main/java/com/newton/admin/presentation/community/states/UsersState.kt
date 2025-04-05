package com.newton.admin.presentation.community.states

import com.newton.admin.data.mappers.*

data class UsersState(
    val getUsersError: String? = null,
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val searchQuery: String = ""
)
