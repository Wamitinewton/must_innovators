package com.newton.admin.presentation.partners.states

import com.newton.admin.presentation.role_management.executives.view.User

data class PartnersState (
    val position:String = "",
    val email:String = "",
    val community:String = "",
    val users:List<User> = emptyList(),
    val isLoading:Boolean = false,
    val isSuccess:Boolean = false,
    val errorMessage:String? = null,
)