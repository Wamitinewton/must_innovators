package com.newton.account.presentation.states

import com.newton.core.domain.models.auth_models.UserData

data class ProfileViewState(
    val errorMessage: String? = null,
    val userData: UserData? = null
)
