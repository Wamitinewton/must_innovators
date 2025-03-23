package com.newton.events.presentation.viewmodel

import com.newton.core.utils.ValidationResult
import javax.inject.Inject

class EventRegistrationValidator @Inject constructor() {


    fun validateEducationLevel(level: String): ValidationResult {
        val result = when {
            level.isBlank() -> ValidationResult(false, "Education level is required")
            else -> ValidationResult(true)
        }
        return result
    }

    fun validateExpectations(expectations: String): ValidationResult {
        val result = when {
            expectations.isBlank() -> ValidationResult(false, "Please share your expectations")
            expectations.length < 10 -> ValidationResult(
                false,
                "Please provide more details about your expectations"
            )

            else -> ValidationResult(true)
        }

        return result
    }

    fun validatePhoneNumber(phoneNumber: String): ValidationResult {
        val result = when {
            phoneNumber.isBlank() -> ValidationResult(false, "Phone number is needed")
            phoneNumber.length < 10 -> ValidationResult(
                false,
                "Phone number must be at least 10 digits"
            )

            !phoneNumber.all { it.isDigit() } -> ValidationResult(
                false,
                "Phone number must contain only digits"
            )

            else -> ValidationResult(true)
        }
        return result
    }


}