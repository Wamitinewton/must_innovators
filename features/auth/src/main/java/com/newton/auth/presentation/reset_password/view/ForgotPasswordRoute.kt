package com.newton.auth.presentation.reset_password.view

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.newton.auth.presentation.reset_password.states.ForgotPasswordEvent
import com.newton.auth.presentation.reset_password.view_model.ForgotPasswordViewModel
import com.newton.core.enums.ForgotPassword

@Composable
fun ForgotPasswordRoute(
    onNavigateToLogin: () -> Unit,
    forgotPasswordViewModel: ForgotPasswordViewModel,
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

        else -> when (state.currentStep) {
            ForgotPassword.EMAIL_INPUT -> {
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
                    otpError = state.error
                )
            }

            ForgotPassword.OTP_INPUT -> {
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
                    onBackPressed = { forgotPasswordViewModel.onEvent(ForgotPasswordEvent.NavigateBack) },
                    resendOtpError = state.error,
                    onResendOtp = { forgotPasswordViewModel.onEvent(ForgotPasswordEvent.RequestOtp) }
                )
            }

            ForgotPassword.PASSWORD_RESET -> {
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
                    onSubmit = { forgotPasswordViewModel.onEvent(ForgotPasswordEvent.ResetPassword) }
                )
            }
        }
    }

    BackHandler(enabled = state.currentStep != ForgotPassword.EMAIL_INPUT) {
        forgotPasswordViewModel.onEvent(ForgotPasswordEvent.NavigateBack)
    }

}