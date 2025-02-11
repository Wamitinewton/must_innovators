package com.newton.auth.presentation.sign_up.event

sealed class SignupUiEvent {
    data class EmailChanged(val email: String): SignupUiEvent()
    data class FirstNameChanged(val firstName: String): SignupUiEvent()
    data class LastNameChanged(val lastname: String): SignupUiEvent()
    data class PasswordChanged(val password: String): SignupUiEvent()
    data class ConfirmPasswordChanged(val confirmPwd: String): SignupUiEvent()
    data class CourseChanged(val course: String): SignupUiEvent()
    data object SignUp: SignupUiEvent()
    data object ClearError: SignupUiEvent()
}

sealed class SignUpNavigationEvent {
   data object NavigateToLogin: SignUpNavigationEvent()
}