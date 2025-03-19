package com.newton.auth.presentation.reset_password.view_model


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.reset_password.events.ForgotPasswordEvent
import com.newton.auth.presentation.reset_password.states.ForgotPasswordState
import com.newton.core.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    authRepository: AuthRepository
) : ViewModel() {

    private val formValidator = ForgotPasswordFormValidator()
    private val stateHolder = ForgotPasswordStateHolder(
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