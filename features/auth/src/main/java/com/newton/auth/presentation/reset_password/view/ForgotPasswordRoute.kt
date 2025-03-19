package com.newton.auth.presentation.reset_password.view

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.newton.auth.presentation.reset_password.events.ForgotPasswordEvent
import com.newton.auth.presentation.reset_password.view_model.ForgotPasswordViewModel
import com.newton.auth.presentation.utils.OtpVerificationScreen
import com.newton.core.enums.AuthFlow
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

        else -> when (state.passwordRecoveryFlow) {
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
                    onBackPressed = { forgotPasswordViewModel.onEvent(ForgotPasswordEvent.NavigateBack) },
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

    BackHandler(enabled = state.passwordRecoveryFlow != AuthFlow.EMAIL_INPUT) {
        forgotPasswordViewModel.onEvent(ForgotPasswordEvent.NavigateBack)
    }

}