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
package com.newton.core.utils


/**
 * Utility class for password validation operations
 */

object PasswordValidator {
    private const val MIN_PASSWORD_LENGTH = 8
    private val specialCharRegex = Regex("[!@#\$%^&*(),.?\":{}|<>]")
    private val uppercaseRegex = Regex("[A-Z]")
    private val lowercaseRegex = Regex("[a-z]")

    /**
     * Validates if the password meets all required criteria
     * @return ValidationResult containing success status and error message if any
     */

    fun validatePassword(password: String,email: String?): ValidationResult {
        return when {
            password.length < MIN_PASSWORD_LENGTH -> {
                ValidationResult(
                    false,
                    "Password must be at least $MIN_PASSWORD_LENGTH characters long"
                )
            }

            !specialCharRegex.containsMatchIn(password) -> {
                ValidationResult(false, "Password must contain at least one special character")
            }

            !uppercaseRegex.containsMatchIn(password) -> {
                ValidationResult(false, "Password must contain at least one uppercase letter")
            }

            !lowercaseRegex.containsMatchIn(password) -> {
                ValidationResult(false, "Password must contain at least one lowercase letter")
            }
            email != null && containsMatchingSequence(password, email) -> {
                ValidationResult(false, "Password should not match email pattern")
            }
            else -> ValidationResult(true)
        }
    }
    private fun containsMatchingSequence(username: String, email: String): Boolean {
        if (username.length < 5 || email.length < 5) {
            return false
        }
        for (i in 0..username.length - 5) {
            val sequence = username.substring(i, i + 5)
            if (email.contains(sequence, ignoreCase = true)) {
                return true
            }
        }

        return false
    }
    /**
     * Validates if the confirm password matches the password
     */
    fun validatePasswordMatch(
        password: String,
        confirmPassword: String
    ): ValidationResult {
        return if (password == confirmPassword) {
            ValidationResult(true)
        } else {
            ValidationResult(false, "Passwords do not match")
        }
    }
}

/**
 * Utility class for input validation operations
 */

object InputValidators {
    private val emailRegex =
        Regex(
            "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
        )

    /**
     * Validates if the input is a valid email address
     */
    fun validateEmail(email: String): ValidationResult {
        return if (email.matches(emailRegex)) {
            ValidationResult(true)
        } else {
            ValidationResult(false, "Invalid email format")
        }
    }

    fun validateName(name: String?): ValidationResult {
        return when {
            name == null || name.trim().isEmpty() -> {
                ValidationResult(false, "Name cannot be empty")
            }

            !name.matches("[A-Za-z\\s]+".toRegex()) -> {
                ValidationResult(false, "Name should only contain letters ")
            }

            else -> {
                ValidationResult(true)
            }
        }
    }
    fun validateUsername(username: String?): ValidationResult{
        return when {
            username == null || username.trim().isEmpty() -> {
                ValidationResult(false, "Username cannot be empty")
            }

            !username.matches("[A-Za-z0-9_]+".toRegex()) -> {
                ValidationResult(false, "Username should only contain letters, numbers, and underscores")
            }
            else -> {
                ValidationResult(true)
            }
        }
    }

    fun validateCourse(course: String?): ValidationResult {
        return when {
            course == null || course.trim().isEmpty() -> {
                ValidationResult(false, "Course cannot be empty")
            }
            !course.matches("[A-Za-z\\s]+".toRegex()) -> {
                ValidationResult(false, "Course should only contain letters and spaces")
            }
            else -> {
                ValidationResult(true)
            }
        }
    }

}

/**
 * Data class to hold validation results
 */

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

object OTPValidator {
    fun validateOtp(
        otp: String,
        onError: (String) -> Unit
    ): Boolean {
        return when {
            otp.isBlank() -> {
                onError("OTP cannot be empty")
                false
            }

            otp.length < 6 -> {
                onError("Invalid OTP")
                false
            }

            else -> true
        }
    }
}
