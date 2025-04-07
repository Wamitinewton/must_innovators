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
package com.newton.auth.presentation.resetPassword.view

import androidx.compose.runtime.*
import com.newton.auth.presentation.resetPassword.events.*
import com.newton.auth.presentation.resetPassword.viewModel.*
import com.newton.auth.presentation.utils.*
import com.newton.core.enums.*

@Composable
fun ForgotPasswordRoute(
    onNavigateToLogin: () -> Unit,
    forgotPasswordViewModel: ForgotPasswordViewModel
) {
    val state by forgotPasswordViewModel.state.collectAsState()

    when {
        state.isPasswordResetComplete -> {
            PasswordResetSuccess(
                onNavigateToLogin = {
                    forgotPasswordViewModel.onEvent(ForgotPasswordEvent.RestartFlow)
                    onNavigateToLogin()
                }
            )
        }

        else ->
            when (state.passwordRecoveryFlow) {
                AuthFlow.EMAIL_INPUT -> {
                    EmailInput(
                        email = state.email,
                        isLoading = state.isLoading,
                        emailError = state.emailError,
                        onEmailChanged = {
                            forgotPasswordViewModel.onEvent(
                                ForgotPasswordEvent.EmailChanged(
                                    it
                                )
                            )
                        },
                        onSubmit = { forgotPasswordViewModel.onEvent(ForgotPasswordEvent.RequestOtp) },
                        onBackPressed = onNavigateToLogin,
                        otpError = state.emailServerError,
                        topBarTitle = "Forgot password",
                        screenTitle = "Create a new password"
                    )
                }

                AuthFlow.OTP_INPUT -> {
                    OtpVerificationScreen(
                        otp = state.otp,
                        isLoading = state.isLoading,
                        otpError = state.otpError,
                        email = state.email,
                        onOtpChanged = {
                            forgotPasswordViewModel.onEvent(
                                ForgotPasswordEvent.OtpChanged(
                                    it
                                )
                            )
                        },
                        onVerifyOtp = { forgotPasswordViewModel.onEvent(ForgotPasswordEvent.VerifyOtp) },
                        onBackPressed = { },
                        resendOtpError = state.otpServerError,
                        onResendOtp = { forgotPasswordViewModel.onEvent(ForgotPasswordEvent.RequestOtp) }
                    )
                }

                AuthFlow.PASSWORD_RESET -> {
                    NewPasswordScreen(
                        newPassword = state.password,
                        confirmPassword = state.confirmPassword,
                        isLoading = state.isLoading,
                        passwordError = state.passwordError,
                        confirmPasswordError = state.confirmPasswordError,
                        onPasswordChanged = {
                            forgotPasswordViewModel.onEvent(
                                ForgotPasswordEvent.PasswordChanged(
                                    it
                                )
                            )
                        },
                        onConfirmPasswordChanged = {
                            forgotPasswordViewModel.onEvent(
                                ForgotPasswordEvent.ConfirmPasswordChanged(
                                    it
                                )
                            )
                        },
                        onSubmit = { forgotPasswordViewModel.onEvent(ForgotPasswordEvent.ResetPassword) },
                        changePasswordError = state.passwordServerError
                    )
                }

                else -> {}
            }
    }
}
