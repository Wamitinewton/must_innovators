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
package com.newton.auth.presentation.resetPassword.viewModel

import com.newton.auth.presentation.resetPassword.states.*
import com.newton.core.enums.*
import com.newton.network.*
import com.newton.network.domain.models.authModels.*
import com.newton.network.domain.repositories.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.*

class ForgotPasswordStateHolder(
    private val authRepository: AuthRepository,
    private val formValidator: ForgotPasswordFormValidator,
    private val coroutineScope: CoroutineScope
) {
    private val _state = MutableStateFlow(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState> = _state.asStateFlow()

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email, emailError = null) }
    }

    fun updateOtp(otp: String) {
        _state.update { it.copy(otp = otp, otpError = null) }
    }

    fun updatePassword(password: String) {
        _state.update { it.copy(password = password, passwordError = null) }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _state.update {
            it.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = null
            )
        }
    }

    fun dismissError() {
        _state.update {
            it.copy(
                otpServerError = null,
                emailServerError = null,
                passwordServerError = null
            )
        }
    }

    fun dismissSuccess() {
        _state.update { it.copy(successMessage = null) }
    }

    fun resetState() {
        _state.update { ForgotPasswordState() }
    }

    fun requestOtp() {
        val email = state.value.email
        val validationResult = formValidator.validateEmail(email)

        if (!validationResult.isValid) {
            _state.update { it.copy(emailError = validationResult.errorMessage) }
            return
        }

        coroutineScope.launch {
            authRepository.requestOtp(OtpRequest(email)).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                emailServerError = result.message
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                passwordRecoveryFlow = AuthFlow.OTP_INPUT,
                                successMessage = "OTP sent to your email"
                            )
                        }
                        Timber.d("Otp requested successfully")
                    }
                }
            }
        }
    }

    fun verifyOtp() {
        val otp = state.value.otp
        val email = state.value.email
        val validationResult = formValidator.validateOtp(otp)

        if (!validationResult.isValid) {
            _state.update { it.copy(otpError = validationResult.errorMessage) }
            return
        }

        coroutineScope.launch {
            _state.update { it.copy(isLoading = true, otpServerError = null) }

            authRepository.verifyOtp(VerifyOtp(otp, email)).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                otpServerError = result.message,
                                otpError = "Invalid OTP"
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                passwordRecoveryFlow = AuthFlow.PASSWORD_RESET,
                                successMessage = "OTP verified successfully"
                            )
                        }
                        Timber.d("OTP verified successfully")
                    }
                }
            }
        }
    }

    fun resetPassword() {
        val password = state.value.password
        val email = state.value.email
        val confirmPassword = state.value.confirmPassword

        val passwordValidation = formValidator.validatePassword(password)
        if (!passwordValidation.isValid) {
            _state.update { it.copy(passwordError = passwordValidation.errorMessage) }
            return
        }

        val matchValidation = formValidator.validatePasswordMatch(password, confirmPassword)
        if (!matchValidation.isValid) {
            _state.update { it.copy(confirmPasswordError = matchValidation.errorMessage) }
            return
        }

        coroutineScope.launch {
            _state.update { it.copy(isLoading = true, passwordServerError = null) }

            authRepository.resetPassword(
                ResetPasswordRequest(password, email)
            ).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                passwordServerError = result.message
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isPasswordResetComplete = true,
                                successMessage = "Password reset successfully"
                            )
                        }
                        Timber.d("Password reset successfully")
                    }
                }
            }
        }
    }

    fun navigateBack() {
        _state.update { state ->
            when (state.passwordRecoveryFlow) {
                AuthFlow.EMAIL_INPUT -> state
                AuthFlow.OTP_INPUT ->
                    state.copy(
                        passwordRecoveryFlow = AuthFlow.EMAIL_INPUT,
                        otp = "",
                        otpError = null
                    )

                AuthFlow.PASSWORD_RESET ->
                    state.copy(
                        passwordRecoveryFlow = AuthFlow.OTP_INPUT,
                        password = "",
                        confirmPassword = "",
                        passwordError = null,
                        confirmPasswordError = null
                    )

                else -> state
            }
        }
    }
}
