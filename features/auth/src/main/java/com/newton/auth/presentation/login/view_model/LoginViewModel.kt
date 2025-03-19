package com.newton.auth.presentation.login.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.login.event.LoginEvent
import com.newton.auth.presentation.login.event.LoginNavigationEvent
import com.newton.auth.presentation.login.state.LoginViewModelState
import com.newton.core.domain.models.auth_models.LoginResultData
import com.newton.core.domain.repositories.AuthRepository
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val stateHolder: LoginStateHolder,
    private val authRepository: AuthRepository
): ViewModel() {

    private val _navigateToLoginSuccess = Channel<LoginNavigationEvent>()
    val navigateToLoginSuccess = _navigateToLoginSuccess.receiveAsFlow()

    val loginUiState: StateFlow<LoginViewModelState> = stateHolder.loginUiState
    val isUserLoggedIn: StateFlow<Boolean> = stateHolder.isUserLoggedIn

    init {
        checkLoginStatus()
    }

    fun onEvent(event: LoginEvent) {
        stateHolder.updateState(event)

        when(event) {
            LoginEvent.Login -> {
                if (stateHolder.validateForm()) {
                    login()
                }
            }
            else -> {}
        }
    }

    private fun login() {
        viewModelScope.launch {
            stateHolder.setLoading(true)

            authRepository.loginWithEmailAndPassword(stateHolder.getLoginRequest())
                .collect { result ->
                    when(result) {
                        is Resource.Error -> {
                            stateHolder.setError(result.message)
                        }
                        is Resource.Loading -> {
                            stateHolder.setLoading(result.isLoading)
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

            stateHolder.setLoggedInStatus(true)
            stateHolder.clearLoginForm()
            _navigateToLoginSuccess.send(LoginNavigationEvent.NavigateToLoginSuccess)
        } catch (e: Exception) {
            stateHolder.setError("Failed to save login credentials: ${e.message}")
            Timber.e(e, "Failed to process login success")
        }
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            val token = authRepository.getAccessToken()
            val refresh = authRepository.getRefreshToken()
            Timber.d("You are using token: $token")
            Timber.d("You are using refresh token: $refresh")
            stateHolder.setLoggedInStatus(!token.isNullOrEmpty())
        }
    }
}