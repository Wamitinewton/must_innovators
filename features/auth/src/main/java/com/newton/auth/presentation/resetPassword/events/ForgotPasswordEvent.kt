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
package com.newton.auth.presentation.resetPassword.events

sealed class ForgotPasswordEvent {
    data class EmailChanged(val email: String) : ForgotPasswordEvent()

    data class OtpChanged(val otp: String) : ForgotPasswordEvent()

    data class PasswordChanged(val password: String) : ForgotPasswordEvent()

    data class ConfirmPasswordChanged(val confirmPassword: String) : ForgotPasswordEvent()

    data object RequestOtp : ForgotPasswordEvent()

    data object VerifyOtp : ForgotPasswordEvent()

    data object ResetPassword : ForgotPasswordEvent()

    data object NavigateBack : ForgotPasswordEvent()

    data object DismissError : ForgotPasswordEvent()

    data object DismissSuccess : ForgotPasswordEvent()

    data object RestartFlow : ForgotPasswordEvent()
}
