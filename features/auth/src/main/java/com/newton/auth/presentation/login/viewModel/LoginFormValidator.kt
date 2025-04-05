package com.newton.auth.presentation.login.viewModel

import com.newton.core.utils.*
import javax.inject.*

class LoginFormValidator
@Inject
constructor() {
    fun validateEmail(email: String): ValidationResult {
        return InputValidators.validateEmail(email)
    }

    fun validatePassword(password: String): ValidationResult {
        return PasswordValidator.validatePassword(password)
    }

    fun validateForm(
        email: String,
        password: String
    ): Pair<ValidationResult, ValidationResult> {
        val emailResult = validateEmail(email)
        val passwordResult = validatePassword(password)
        return Pair(emailResult, passwordResult)
    }
}
