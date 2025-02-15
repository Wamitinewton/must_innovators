package com.newton.auth.presentation.login.state

import com.newton.core.domain.models.auth_models.LoginResultData

data class LoginViewModelState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val emailInput: String = "",
    val passwordInput: String = "",
    val resultData: LoginResultData? = null,
    val showPassword: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
)
