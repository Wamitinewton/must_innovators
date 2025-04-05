package com.newton.auth.presentation.login.viewModel

import androidx.lifecycle.*
import com.newton.auth.presentation.login.event.*
import com.newton.auth.presentation.login.state.*
import com.newton.core.domain.models.authModels.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import timber.log.*
import javax.inject.*

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val stateHolder: LoginStateHolder,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _navigateToLoginSuccess = Channel<LoginNavigationEvent>()
    val navigateToLoginSuccess = _navigateToLoginSuccess.receiveAsFlow()

    val loginUiState: StateFlow<LoginViewModelState> = stateHolder.loginUiState
    val isUserLoggedIn: StateFlow<Boolean> = stateHolder.isUserLoggedIn

    init {
        checkLoginStatus()
    }

    fun onEvent(event: LoginEvent) {
        stateHolder.updateState(event)

        when (event) {
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
                    when (result) {
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
