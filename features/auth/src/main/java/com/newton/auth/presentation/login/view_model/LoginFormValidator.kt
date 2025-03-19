package com.newton.auth.presentation.login.view_model


import com.newton.core.utils.InputValidators
import com.newton.core.utils.PasswordValidator
import com.newton.core.utils.ValidationResult
import javax.inject.Inject

class LoginFormValidator @Inject constructor() {

     fun validateEmail(email: String): ValidationResult {
        return InputValidators.validateEmail(email)
    }

     fun validatePassword(password: String): ValidationResult {
        return PasswordValidator.validatePassword(password)
    }

    fun validateForm(email: String, password: String): Pair<ValidationResult, ValidationResult> {
        val emailResult = validateEmail(email)
        val passwordResult = validatePassword(password)
        return Pair(emailResult, passwordResult)
    }


}

