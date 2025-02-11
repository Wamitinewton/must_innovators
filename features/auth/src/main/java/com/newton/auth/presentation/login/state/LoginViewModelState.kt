package com.newton.auth.presentation.login.state

import com.newton.auth.domain.models.login.LoginResultData

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
