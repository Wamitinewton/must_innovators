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
package com.newton.auth.presentation.login.viewModel

import com.newton.auth.presentation.login.event.*
import com.newton.auth.presentation.login.state.*
import com.newton.network.domain.models.authModels.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class LoginStateHolder
@Inject
constructor(
    private val validator: LoginFormValidator
) {
    private val _loginUiState = MutableStateFlow(LoginViewModelState())
    val loginUiState: StateFlow<LoginViewModelState> = _loginUiState

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    fun updateState(event: LoginEvent) {
        when (event) {
            LoginEvent.ClearError -> clearError()
            is LoginEvent.EmailChanged -> emailChanged(event.email)
            is LoginEvent.PasswordChanged -> passwordChanged(event.password)
            else -> {}
        }
    }

    private fun clearError() {
        _loginUiState.update { it.copy(errorMessage = null) }
    }

    private fun emailChanged(email: String) {
        _loginUiState.update {
            it.copy(
                emailInput = email,
                emailError = null
            )
        }
        validateEmailInput()
    }

    private fun passwordChanged(password: String) {
        _loginUiState.update {
            it.copy(
                passwordInput = password,
                passwordError = null
            )
        }
        validatePasswordInput()
    }

    private fun validateEmailInput() {
        val emailResult = validator.validateEmail(_loginUiState.value.emailInput)
        if (!emailResult.isValid) {
            _loginUiState.update {
                it.copy(emailError = emailResult.errorMessage)
            }
        }
    }

    private fun validatePasswordInput() {
        val passwordResult = validator.validatePassword(_loginUiState.value.passwordInput)
        if (!passwordResult.isValid) {
            _loginUiState.update {
                it.copy(passwordError = passwordResult.errorMessage)
            }
        }
    }

    fun validateForm(): Boolean {
        val (emailResult, passwordResult) =
            validator.validateForm(
                _loginUiState.value.emailInput,
                _loginUiState.value.passwordInput
            )

        _loginUiState.update {
            it.copy(
                emailError = if (!emailResult.isValid) emailResult.errorMessage else null,
                passwordError = if (!passwordResult.isValid) passwordResult.errorMessage else null
            )
        }

        return emailResult.isValid && passwordResult.isValid
    }

    fun getLoginRequest(): LoginRequest {
        return LoginRequest(
            email = _loginUiState.value.emailInput,
            password = _loginUiState.value.passwordInput
        )
    }

    fun setLoading(isLoading: Boolean) {
        _loginUiState.update { it.copy(isLoading = isLoading) }
    }

    fun setError(message: String?) {
        _loginUiState.update {
            it.copy(
                errorMessage = message,
                isLoading = false
            )
        }
    }

    fun clearLoginForm() {
        _loginUiState.update {
            it.copy(
                emailInput = "",
                passwordInput = "",
                emailError = null,
                passwordError = null,
                errorMessage = null,
                isLoading = false
            )
        }
    }

    fun setLoggedInStatus(isLoggedIn: Boolean) {
        _isUserLoggedIn.value = isLoggedIn
    }
}
