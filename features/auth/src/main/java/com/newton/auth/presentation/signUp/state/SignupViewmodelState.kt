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
package com.newton.auth.presentation.signUp.state

import com.newton.core.enums.*

data class SignupViewmodelState(
    val otp: String = "",
    val userName: String = "",
    val isLoading: Boolean = false,
    val isChecked: Boolean = false,
    val errorMessage: String? = null,
    val firstNameInput: String = "",
    val lastNameInput: String = "",
    val emailInput: String = "",
    val courseName: String = "",
    val passwordInput: String = "",
    val confirmPassword: String = "",
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val emailError: String? = null,
    val success: String? = null,
    val authFlow: AuthFlow = AuthFlow.SIGN_UP,
    val otpServerError: String? = null,
    val otpError: String? = null
)
