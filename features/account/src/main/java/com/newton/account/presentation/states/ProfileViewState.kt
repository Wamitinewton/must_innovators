package com.newton.account.presentation.states

import com.newton.core.domain.models.authModels.*

data class ProfileViewState(
    val errorMessage: String? = null,
    val userData: UserData? = null
)

data class DeleteAccountState(
    val isLoading: Boolean = false,
    val isDeleted: Boolean = false,
    val errorMessage: String? = null
)

data class LogoutState(
    val isLoading: Boolean = false,
    val isLoggedOut: Boolean = false,
    val errorMessage: String? = null
)
