package com.newton.auth.presentation.sign_up.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.newton.auth.presentation.sign_up.event.SignupUiEvent
import com.newton.auth.presentation.sign_up.viewmodel.SignupViewModel
import com.newton.auth.presentation.utils.OtpVerificationScreen
import com.newton.core.enums.AuthFlow

@Composable
fun SignupRoute(
    signupViewModel: SignupViewModel,
    onNavigateToOnboarding: () -> Unit,
    onContinueToLogin: () -> Unit,
) {
    val state by signupViewModel.authUiState.collectAsState()

    when (state.authFlow) {
        AuthFlow.SIGN_UP -> SignupScreen(
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
                onResendOtp = {  },
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