package com.newton.auth.presentation.login.event

sealed class LoginEvent {
    data class EmailChanged(val email: String): LoginEvent()
    data class PasswordChanged(val password: String): LoginEvent()
    data object Login: LoginEvent()
    data object ClearError: LoginEvent()
}

sealed class LoginNavigationEvent {
    data object NavigateToHomeScreen: LoginNavigationEvent()
}