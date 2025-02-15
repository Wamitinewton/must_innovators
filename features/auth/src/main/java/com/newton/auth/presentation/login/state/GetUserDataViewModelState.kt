package com.newton.auth.presentation.login.state

import com.newton.core.domain.models.auth_models.UserData


data class GetUserDataViewModelState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val userData: UserData? = null
)
