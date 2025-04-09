/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
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

        return PasswordValidator.validatePassword(password,null)
    }

    fun validatePasswordMatch(
        password: String,
        confirmPassword: String
    ): ValidationResult {
        return PasswordValidator.validatePasswordMatch(password, confirmPassword)
    }
}
