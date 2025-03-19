package com.newton.auth.presentation.sign_up.viewmodel


import com.newton.core.utils.InputValidators
import com.newton.core.utils.OTPValidator
import com.newton.core.utils.PasswordValidator
import com.newton.core.utils.ValidationResult
import javax.inject.Inject

class SignupFormValidator @Inject constructor() {

    fun validateEmail(email: String): ValidationResult {
        return InputValidators.validateEmail(email)
    }

    fun validatePassword(password: String): ValidationResult {
        return PasswordValidator.validatePassword(password)
    }

    fun validatePasswordMatch(password: String, confirmPassword: String): ValidationResult {
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