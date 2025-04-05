package com.newton.auth.presentation.login.state

import com.newton.core.domain.models.authModels.*

data class GetUserDataViewModelState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val userData: UserData? = null
)
