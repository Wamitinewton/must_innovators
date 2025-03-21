package com.newton.auth.presentation.sign_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.sign_up.event.SignupUiEvent
import com.newton.auth.presentation.sign_up.state.SignupViewmodelState
import com.newton.core.domain.repositories.AuthRepository
import com.newton.core.enums.AuthFlow
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val stateHolder: SignupStateHolder,
    private val authRepository: AuthRepository
): ViewModel() {



    val authUiState: StateFlow<SignupViewmodelState> = stateHolder.signUpState

    fun onEvent(event: SignupUiEvent) {
        stateHolder.updateState(event)

        when(event) {
            is SignupUiEvent.SignUp -> createUserWithEmailAndPassword()
            is SignupUiEvent.VerifyOtp -> verifyAuthOtp()
            else -> {}
        }
    }

    private fun createUserWithEmailAndPassword() {
        if (!stateHolder.isFormValid()) {
            return
        }

        viewModelScope.launch {
            try {
                stateHolder.setLoading(true)

                authRepository.createUserWithEmailAndPassword(stateHolder.getSignupRequest())
                    .onEach { result ->
                        when(result) {
                            is Resource.Error -> {
                                stateHolder.setError(result.message)
                            }
                            is Resource.Loading -> {
                                stateHolder.setLoading(result.isLoading)
                            }
                            is Resource.Success -> {
                                stateHolder.setSuccess(result.message, AuthFlow.OTP_INPUT)
                            }
                        }
                    }.launchIn(this)
            } catch (e: Exception) {
                stateHolder.setLoading(false)
                stateHolder.setError(e.message)
            }
        }
    }

    private fun verifyAuthOtp() {
        if (!stateHolder.validateOtp(authUiState.value.otp)) return

        viewModelScope.launch {
            stateHolder.setLoading(true)
            stateHolder.setOtpServerError(null)

            authRepository.verifyOtp(stateHolder.getVerifyOtpRequest())
                .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        stateHolder.setLoading(false)
                        stateHolder.setOtpServerError(result.message)
                    }
                    is Resource.Loading -> {
                        stateHolder.setLoading(result.isLoading)
                    }
                    is Resource.Success -> {
                        stateHolder.setSuccess(result.message, AuthFlow.SUCCESS)
                        Timber.d("OTP verified successfully")
                    }
                }
            }.launchIn(this)
        }
    }
}