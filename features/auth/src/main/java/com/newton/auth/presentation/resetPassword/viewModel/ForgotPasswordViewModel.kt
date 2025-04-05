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

import androidx.lifecycle.*
import com.newton.auth.presentation.resetPassword.events.*
import com.newton.auth.presentation.resetPassword.states.*
import com.newton.core.domain.repositories.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class ForgotPasswordViewModel
@Inject
constructor(
    authRepository: AuthRepository
) : ViewModel() {
    private val formValidator = ForgotPasswordFormValidator()
    private val stateHolder =
        ForgotPasswordStateHolder(
            authRepository = authRepository,
            formValidator = formValidator,
            coroutineScope = viewModelScope
        )

    val state: StateFlow<ForgotPasswordState> = stateHolder.state

    fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.EmailChanged -> {
                stateHolder.updateEmail(event.email)
            }

            is ForgotPasswordEvent.OtpChanged -> {
                stateHolder.updateOtp(event.otp)
            }

            is ForgotPasswordEvent.PasswordChanged -> {
                stateHolder.updatePassword(event.password)
            }

            is ForgotPasswordEvent.ConfirmPasswordChanged -> {
                stateHolder.updateConfirmPassword(event.confirmPassword)
            }

            is ForgotPasswordEvent.RequestOtp -> {
                stateHolder.requestOtp()
            }

            is ForgotPasswordEvent.VerifyOtp -> {
                stateHolder.verifyOtp()
            }

            is ForgotPasswordEvent.ResetPassword -> {
                stateHolder.resetPassword()
            }

            is ForgotPasswordEvent.NavigateBack -> {
                stateHolder.navigateBack()
            }

            is ForgotPasswordEvent.DismissError -> {
                stateHolder.dismissError()
            }

            is ForgotPasswordEvent.DismissSuccess -> {
                stateHolder.dismissSuccess()
            }

            is ForgotPasswordEvent.RestartFlow -> {
                stateHolder.resetState()
            }
        }
    }
}
