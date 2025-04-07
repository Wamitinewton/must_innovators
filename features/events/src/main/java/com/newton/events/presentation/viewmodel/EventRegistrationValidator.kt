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
package com.newton.events.presentation.viewmodel

import com.newton.core.utils.*
import javax.inject.*

class EventRegistrationValidator
@Inject
constructor() {
    fun validateEducationLevel(level: String): ValidationResult {
        val result =
            when {
                level.isBlank() -> ValidationResult(false, "Education level is required")
                else -> ValidationResult(true)
            }
        return result
    }

    fun validateExpectations(expectations: String): ValidationResult {
        val result =
            when {
                expectations.isBlank() -> ValidationResult(false, "Please share your expectations")
                expectations.length < 10 ->
                    ValidationResult(
                        false,
                        "Please provide more details about your expectations"
                    )

                else -> ValidationResult(true)
            }

        return result
    }

    fun validatePhoneNumber(phoneNumber: String): ValidationResult {
        val result =
            when {
                phoneNumber.isBlank() -> ValidationResult(false, "Phone number is needed")
                phoneNumber.length < 10 ->
                    ValidationResult(
                        false,
                        "Phone number must be at least 10 digits"
                    )

                !phoneNumber.all { it.isDigit() } ->
                    ValidationResult(
                        false,
                        "Phone number must contain only digits"
                    )

                else -> ValidationResult(true)
            }
        return result
    }
}
