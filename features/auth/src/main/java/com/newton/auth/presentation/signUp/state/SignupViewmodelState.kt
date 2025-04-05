package com.newton.auth.presentation.signUp.state

import com.newton.core.enums.*

data class SignupViewmodelState(
    val otp: String = "",
    val userName: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val firstNameInput: String = "",
    val lastNameInput: String = "",
    val emailInput: String = "",
    val courseName: String = "",
    val passwordInput: String = "",
    val confirmPassword: String = "",
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val emailError: String? = null,
    val success: String? = null,
    val authFlow: AuthFlow = AuthFlow.SIGN_UP,
    val otpServerError: String? = null,
    val otpError: String? = null
)
