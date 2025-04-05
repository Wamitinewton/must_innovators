package com.newton.auth.presentation.signUp.view

import androidx.compose.runtime.*
import com.newton.auth.presentation.signUp.event.*
import com.newton.auth.presentation.signUp.viewmodel.*
import com.newton.auth.presentation.utils.*
import com.newton.core.enums.*

@Composable
fun SignupRoute(
    signupViewModel: SignupViewModel,
    onNavigateToOnboarding: () -> Unit,
    onContinueToLogin: () -> Unit
) {
    val state by signupViewModel.authUiState.collectAsState()

    when (state.authFlow) {
        AuthFlow.SIGN_UP ->
            SignupScreen(
                signupViewModel = signupViewModel,
                onNavigateToOnBoarding = onNavigateToOnboarding
            )

        AuthFlow.OTP_INPUT -> {
            OtpVerificationScreen(
                otp = state.otp,
                isLoading = state.isLoading,
                otpError = state.otpError,
                email = state.emailInput,
                onOtpChanged = {
                    signupViewModel.onEvent(
                        SignupUiEvent.OtpChanged(
                            it
                        )
                    )
                },
                onResendOtp = { },
                onVerifyOtp = { signupViewModel.onEvent(SignupUiEvent.VerifyOtp) },
                resendOtpError = state.otpServerError,
                onBackPressed = {}
            )
        }

        AuthFlow.SUCCESS -> {
            SignupSuccessScreen(
                onContinueClick = onContinueToLogin
            )
        }

        else -> {}
    }
}
