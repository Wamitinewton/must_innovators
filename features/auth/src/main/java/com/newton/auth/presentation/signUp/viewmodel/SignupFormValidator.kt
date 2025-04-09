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
package com.newton.auth.presentation.signUp.viewmodel

import com.newton.core.utils.*
import javax.inject.*

class SignupFormValidator
@Inject
constructor() {
    fun validateEmail(email: String): ValidationResult {
        return InputValidators.validateEmail(email)
    }

    fun validatePassword(password: String, email: String?): ValidationResult {
        return PasswordValidator.validatePassword(password, email)
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

    fun validateName(name: String): ValidationResult {
        return InputValidators.validateName(name)
    }
    fun validateCourse(course: String): ValidationResult {
        return InputValidators.validateCourse(course)
    }

    fun validateUsername(username: String): ValidationResult {
        return InputValidators.validateUsername(username)
    }
}
