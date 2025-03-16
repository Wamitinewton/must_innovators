package com.newton.auth.presentation.reset_password.events

import com.newton.core.enums.ForgotPassword

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
    val error: String? = null,
    val successMessage: String? = null,
    val currentStep: ForgotPassword = ForgotPassword.EMAIL_INPUT,
    val isPasswordResetComplete: Boolean = false
)
