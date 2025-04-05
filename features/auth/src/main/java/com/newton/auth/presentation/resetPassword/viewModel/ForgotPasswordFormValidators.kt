package com.newton.auth.presentation.resetPassword.viewModel

import com.newton.core.utils.*

class ForgotPasswordFormValidator {
    fun validateEmail(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(isValid = false, errorMessage = "Email cannot be empty")
        }

        return InputValidators.validateEmail(email)
    }

    fun validateOtp(otp: String): ValidationResult {
        if (otp.isBlank()) {
            return ValidationResult(isValid = false, errorMessage = "OTP cannot be empty")
        }

        if (otp.length != 6 || !otp.all { it.isDigit() }) {
            return ValidationResult(isValid = false, errorMessage = "OTP must be 6 digits")
        }

        return ValidationResult(isValid = true)
    }

    fun validatePassword(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(isValid = false, errorMessage = "Password cannot be empty")
        }

        return PasswordValidator.validatePassword(password)
    }

    fun validatePasswordMatch(
        password: String,
        confirmPassword: String
    ): ValidationResult {
        return PasswordValidator.validatePasswordMatch(password, confirmPassword)
    }
}
