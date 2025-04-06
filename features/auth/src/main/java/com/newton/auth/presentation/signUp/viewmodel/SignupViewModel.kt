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
package com.newton.auth.presentation.signUp.viewmodel

import androidx.lifecycle.*
import com.newton.auth.presentation.signUp.event.*
import com.newton.auth.presentation.signUp.state.*
import com.newton.core.enums.*
import com.newton.network.*
import com.newton.network.domain.repositories.*
import com.newton.sharedprefs.domain.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.*
import javax.inject.*

@HiltViewModel
class SignupViewModel
@Inject
constructor(
    private val stateHolder: SignupStateHolder,
    private val authRepository: AuthRepository,
    private val prefsRepository: PrefsRepository
) : ViewModel() {
    val authUiState: StateFlow<SignupViewmodelState> = stateHolder.signUpState

    init {
        if (prefsRepository.isVerificationPending()) {
            val savedEmail = prefsRepository.getUserEmail()
            if (savedEmail.isNotEmpty()) {
                stateHolder.updateState(SignupUiEvent.EmailChanged(savedEmail))

                stateHolder.setSuccess("Please verify your email", AuthFlow.OTP_INPUT)
            }
        }
    }

    fun onEvent(event: SignupUiEvent) {
        stateHolder.updateState(event)

        when (event) {
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

                val signupRequest = stateHolder.getSignupRequest()
                prefsRepository.setUserEmail(signupRequest.email)
                prefsRepository.setVerificationPending(true)

                authRepository.createUserWithEmailAndPassword(stateHolder.getSignupRequest())
                    .onEach { result ->
                        when (result) {
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

                            prefsRepository.setVerificationPending(false)
                        }
                    }
                }.launchIn(this)
        }
    }
}
