package com.newton.admin.presentation.community.states

import com.newton.admin.presentation.role_management.executives.view.User

data class UsersState(
    val getUsersError:String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val users: List<User> = emptyList()
)
