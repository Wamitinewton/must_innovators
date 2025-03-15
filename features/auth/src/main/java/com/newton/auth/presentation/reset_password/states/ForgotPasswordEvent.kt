package com.newton.auth.presentation.reset_password.states

sealed class ForgotPasswordEvent {
    data class EmailChanged(val email: String) : ForgotPasswordEvent()
    data class OtpChanged(val otp: String) : ForgotPasswordEvent()
    data class PasswordChanged(val password: String) : ForgotPasswordEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : ForgotPasswordEvent()
    data object RequestOtp : ForgotPasswordEvent()
    data object VerifyOtp : ForgotPasswordEvent()
    data object ResetPassword : ForgotPasswordEvent()
    data object NavigateBack : ForgotPasswordEvent()
    data object DismissError : ForgotPasswordEvent()
    data object DismissSuccess : ForgotPasswordEvent()
    data object RestartFlow : ForgotPasswordEvent()
}