package com.newton.auth.presentation.reset_password.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.reset_password.states.ForgotPasswordState
import com.newton.auth.presentation.reset_password.events.ForgotPasswordEvent
import com.newton.core.domain.models.auth_models.OtpRequest
import com.newton.core.domain.models.auth_models.ResetPasswordRequest
import com.newton.core.domain.models.auth_models.VerifyOtp
import com.newton.core.domain.repositories.AuthRepository
import com.newton.core.enums.ForgotPassword
import com.newton.core.utils.InputValidators
import com.newton.core.utils.PasswordValidator
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState> = _state.asStateFlow()

    fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.EmailChanged -> {
                _state.update { it.copy(email = event.email, emailError = null) }
            }

            is ForgotPasswordEvent.OtpChanged -> {
                _state.update { it.copy(otp = event.otp, otpError = null) }
            }

            is ForgotPasswordEvent.PasswordChanged -> {
                _state.update { it.copy(password = event.password, passwordError = null) }
            }

            is ForgotPasswordEvent.ConfirmPasswordChanged -> {
                _state.update {
                    it.copy(
                        confirmPassword = event.confirmPassword,
                        confirmPasswordError = null
                    )
                }
            }

            is ForgotPasswordEvent.RequestOtp -> {
                requestOtp()
            }

            is ForgotPasswordEvent.VerifyOtp -> {
                verifyOtp()
            }

            is ForgotPasswordEvent.ResetPassword -> {
                resetPassword()
            }

            is ForgotPasswordEvent.NavigateBack -> {
                navigateBack()
            }

            is ForgotPasswordEvent.DismissError -> {
                _state.update { it.copy(otpServerError = null, emailServerError = null, passwordServerError = null) }
            }

            is ForgotPasswordEvent.DismissSuccess -> {
                _state.update { it.copy(successMessage = null) }
            }

            is ForgotPasswordEvent.RestartFlow -> {
                _state.update { ForgotPasswordState() }
            }
        }
    }

    private fun requestOtp() {
        val email = state.value.email

        if (!validateEmail(email)) return

        viewModelScope.launch {
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
                                currentStep = ForgotPassword.OTP_INPUT,
                                successMessage = "OTP sent to your email"
                            )
                        }
                        Timber.d("Otp requested successfully")
                    }
                }
            }
        }
    }

    private fun verifyOtp() {
        val otp = state.value.otp
        val email = state.value.email
        if (!validateOtp(otp)) return

        viewModelScope.launch {
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
                                currentStep = ForgotPassword.PASSWORD_RESET,
                                successMessage = "OTP verified successfully"
                            )
                        }
                        Timber.d("OTP verified successfully")
                    }
                }
            }
        }
    }

    private fun resetPassword() {
        val password = state.value.password
        val email = state.value.email
        val confirmPassword = state.value.confirmPassword
        if (!validatePassword(password, confirmPassword)) return

        viewModelScope.launch {
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

    private fun navigateBack() {
        _state.update { state ->
            when (state.currentStep) {
                ForgotPassword.EMAIL_INPUT -> state
                ForgotPassword.OTP_INPUT -> state.copy(
                    currentStep = ForgotPassword.EMAIL_INPUT,
                    otp = "",
                    otpError = null
                )

                ForgotPassword.PASSWORD_RESET -> state.copy(
                    currentStep = ForgotPassword.OTP_INPUT,
                    password = "",
                    confirmPassword = "",
                    passwordError = null,
                    confirmPasswordError = null
                )
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        if (email.isBlank()) {
            _state.update { it.copy(emailError = "Email cannot be empty") }
            return false
        }
        val validationResult = InputValidators.validateEmail(email)
        if (!validationResult.isValid) {
            _state.update { it.copy(emailError = validationResult.errorMessage) }
            return false
        }
        return true
    }

    private fun validateOtp(otp: String): Boolean {
        return if (otp.isBlank()) {
            _state.update { it.copy(otpError = "OTP cannot be empty") }
            false
        } else if (otp.length < 6) {
            _state.update { it.copy(otpError = "Invalid OTP") }
            false
        } else {
            true
        }
    }

    private fun validatePassword(password: String, confirmPassword: String): Boolean {
        if (password.isBlank()) {
            _state.update { it.copy(passwordError = "Password cannot be empty") }
            return false
        }

        val passwordValidation = PasswordValidator.validatePassword(password)
        if (!passwordValidation.isValid) {
            _state.update { it.copy(passwordError = passwordValidation.errorMessage) }
            return false
        }

        val matchValidation = PasswordValidator.validatePasswordMatch(password, confirmPassword)
        if (!matchValidation.isValid) {
            _state.update { it.copy(confirmPasswordError = matchValidation.errorMessage) }
            return false
        }

        return true
    }
}