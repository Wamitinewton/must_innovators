package com.newton.auth.presentation.sign_up.state

data class SignupViewmodelState(
    val userName:String = "",
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
    val success: String? = null
)
