package com.newton.admin.presentation.community.states

import com.newton.admin.data.mappers.User

data class UsersState(
    val getUsersError:String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val users: List<User> = emptyList()
)
