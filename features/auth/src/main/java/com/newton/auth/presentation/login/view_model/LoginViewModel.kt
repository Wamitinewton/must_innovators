package com.newton.auth.presentation.login.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.domain.models.auth_models.LoginRequest
import com.newton.core.domain.models.auth_models.LoginResultData
import com.newton.core.domain.repositories.AuthRepository
import com.newton.auth.presentation.login.event.LoginEvent
import com.newton.auth.presentation.login.event.LoginNavigationEvent
import com.newton.auth.presentation.login.state.LoginViewModelState
import com.newton.core.utils.InputValidators
import com.newton.core.utils.PasswordValidator
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _navigateToLoginSuccess = Channel<LoginNavigationEvent>()
    val navigateToLoginSuccess = _navigateToLoginSuccess.receiveAsFlow()

    private val _loginUiState:MutableStateFlow<LoginViewModelState> = MutableStateFlow(LoginViewModelState())
    val loginUiState: StateFlow<LoginViewModelState> get() = _loginUiState

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> get() = _isUserLoggedIn

    init {
       checkLoginStatus()
    }

    fun onEvent(event: LoginEvent) {
        when(event) {
            LoginEvent.ClearError -> {
                _loginUiState.update { it.copy(errorMessage = null) }
            }
            is LoginEvent.EmailChanged -> {
                _loginUiState.update {
                    it.copy(
                        emailInput = event.email,
                        emailError = null
                    )
                }
                validateEmailInput()
            }
            LoginEvent.Login -> {
                if (validateForm()) {
                    login()
                }
            }
            is LoginEvent.PasswordChanged -> {
                _loginUiState.update {
                    it.copy(
                        passwordInput = event.password,
                        passwordError = null
                    )
                }
                validatePasswordInput()
            }
        }
    }

    private fun validateEmailInput() {
        val emailResult = InputValidators.validateEmail(_loginUiState.value.emailInput)
        if (!emailResult.isValid) {
            _loginUiState.update {
                it.copy(emailError = emailResult.errorMessage)
            }
        }
    }

    private fun validatePasswordInput() {
        val passwordResult = InputValidators.validateEmail(_loginUiState.value.passwordInput)
        if (!passwordResult.isValid) {
            _loginUiState.update {
                it.copy(passwordError = passwordResult.errorMessage)
            }
        }
    }

    private fun validateForm(): Boolean {
        val emailResult = InputValidators.validateEmail(_loginUiState.value.emailInput)
        val passwordResult = PasswordValidator.validatePassword(_loginUiState.value.passwordInput)

        _loginUiState.update {
            it.copy(
                emailError = if (!emailResult.isValid) emailResult.errorMessage else null,
                passwordError = if (!passwordResult.isValid) passwordResult.errorMessage else null
            )
        }

        return emailResult.isValid && passwordResult.isValid
    }

    private fun login() {
        viewModelScope.launch {
            val loginRequest = LoginRequest(
                email = _loginUiState.value.emailInput,
                password = _loginUiState.value.passwordInput
            )

            authRepository.loginWithEmailAndPassword(loginRequest)
                .collect { result ->
                    when(result) {
                        is Resource.Error -> {
                            _loginUiState.update {
                                it.copy(
                                    errorMessage = result.message,
                                    isLoading = false
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _loginUiState.update { it.copy(isLoading = result.isLoading) }
                        }
                        is Resource.Success -> {
                            result.data?.let { handleLoginSuccess(it) }
                        }
                    }
                }
        }
    }

    private suspend fun handleLoginSuccess(loginResultData: LoginResultData) {
        try {
            authRepository.storeAuthTokens(
                accessToken = loginResultData.access,
                refreshToken = loginResultData.refresh
            )

            _isUserLoggedIn.value = true

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
            _navigateToLoginSuccess.send(LoginNavigationEvent.NavigateToLoginSuccess)
        } catch (e: Exception) {
            _loginUiState.update {
                it.copy(
                    errorMessage = "Failed to save login credentials: ${e.message}",
                    isLoading = false
                )
            }
            Timber.e(e, "Failed to process login success")
        }
    }

   private fun checkLoginStatus() {
       viewModelScope.launch {
           val token = authRepository.getAccessToken()
           val refresh = authRepository.getRefreshToken()
           Timber.d("You are using token: $token")
           Timber.d("You are using refresh token: $refresh")
           _isUserLoggedIn.update { !token.isNullOrEmpty() }

       }
   }
}