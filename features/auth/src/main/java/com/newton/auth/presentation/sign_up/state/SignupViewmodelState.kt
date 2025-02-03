package com.newton.auth.presentation.sign_up.state

import com.newton.auth.domain.models.sign_up.SignupResponse

data class SignupViewmodelState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val firstNameInput: String = "",
    val lastNameInput: String = "",
    val emailInput: String = "",
    val userName: String = "",
    val registrationNo: String = "",
    val courseName: String = "",
    val passwordInput: String = "",
    val confirmPassword: String = "",
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val emailError: String? = null,
    val registrationNoError: String? = null,
    val signupResponse: SignupResponse? = null
)
