package com.newton.auth.presentation.login.view_model


import com.newton.auth.presentation.login.event.LoginEvent
import com.newton.auth.presentation.login.state.LoginViewModelState
import com.newton.core.domain.models.auth_models.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LoginStateHolder @Inject constructor(
    private val validator: LoginFormValidator
) {
    private val _loginUiState = MutableStateFlow(LoginViewModelState())
    val loginUiState: StateFlow<LoginViewModelState> = _loginUiState

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    fun updateState(event: LoginEvent) {
        when(event) {
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
        val (emailResult, passwordResult) = validator.validateForm(
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
        _loginUiState.update { it.copy(
            errorMessage = message,
            isLoading = false
        ) }
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