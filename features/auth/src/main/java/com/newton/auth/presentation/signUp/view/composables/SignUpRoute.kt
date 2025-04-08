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
package com.newton.auth.presentation.signUp.view.composables

import androidx.compose.runtime.*
import com.newton.auth.presentation.signUp.event.*
import com.newton.auth.presentation.signUp.view.SignupScreen
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
