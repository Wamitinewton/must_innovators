package com.newton.auth.presentation.login.state

import com.newton.auth.domain.models.get_user.UserData

data class GetUserDataViewModelState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val userData: UserData? = null
)
