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
package com.newton.auth.presentation.signUp.event

sealed class SignupUiEvent {
    data class EmailChanged(val email: String) : SignupUiEvent()

    data class FirstNameChanged(val firstName: String) : SignupUiEvent()

    data class LastNameChanged(val lastname: String) : SignupUiEvent()

    data class PasswordChanged(val password: String,val email: String) : SignupUiEvent()

    data class ConfirmPasswordChanged(val confirmPwd: String) : SignupUiEvent()

    data class OnCheckedChanged(val checked: Boolean) : SignupUiEvent()

    data class CourseChanged(val course: String) : SignupUiEvent()

    data object SignUp : SignupUiEvent()

    data object VerifyOtp : SignupUiEvent()

    data object ClearError : SignupUiEvent()

    data class UsernameChanged(val username: String) : SignupUiEvent()

    data class OtpChanged(val otp: String) : SignupUiEvent()
}
