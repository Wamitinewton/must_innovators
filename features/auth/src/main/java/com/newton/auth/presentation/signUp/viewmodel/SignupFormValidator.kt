package com.newton.auth.presentation.signUp.viewmodel

import com.newton.core.utils.*
import javax.inject.*

class SignupFormValidator
@Inject
constructor() {
    fun validateEmail(email: String): ValidationResult {
        return InputValidators.validateEmail(email)
    }

    fun validatePassword(password: String): ValidationResult {
        return PasswordValidator.validatePassword(password)
    }

    fun validatePasswordMatch(
        password: String,
        confirmPassword: String
    ): ValidationResult {
        return PasswordValidator.validatePasswordMatch(password, confirmPassword)
    }

    fun validateOtp(otp: String): ValidationResult {
        val isValid = OTPValidator.validateOtp(otp) { _ -> }
        return ValidationResult(
            isValid = isValid,
            errorMessage = if (isValid) null else "Invalid OTP"
        )
    }
}
