package com.newton.auth.presentation.resetPassword.states

import com.newton.core.enums.*

data class ForgotPasswordState(
    val email: String = "",
    val otp: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailError: String? = null,
    val otpError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val passwordRecoveryFlow: AuthFlow = AuthFlow.EMAIL_INPUT,
    val isPasswordResetComplete: Boolean = false,
    val emailServerError: String? = null,
    val otpServerError: String? = null,
    val passwordServerError: String? = null
)
