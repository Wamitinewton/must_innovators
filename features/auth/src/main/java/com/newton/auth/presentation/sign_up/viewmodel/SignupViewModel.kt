package com.newton.auth.presentation.sign_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.domain.models.sign_up.SignupRequest
import com.newton.auth.domain.repositories.AuthRepository
import com.newton.auth.presentation.sign_up.event.SignUpNavigationEvent
import com.newton.auth.presentation.sign_up.event.SignupUiEvent
import com.newton.auth.presentation.sign_up.state.SignupViewmodelState
import com.newton.core.utils.InputValidators
import com.newton.core.utils.PasswordValidator
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _navigateToLogin = Channel<SignUpNavigationEvent>()
    val navigateToLogin = _navigateToLogin.receiveAsFlow()

    private val _signUpState: MutableStateFlow<SignupViewmodelState> = MutableStateFlow(SignupViewmodelState())
    val authUiState: StateFlow<SignupViewmodelState> get() = _signUpState

    fun onEvent(event: SignupUiEvent) {
        when(event) {
            SignupUiEvent.ClearError -> clearErrors()
            is SignupUiEvent.ConfirmPasswordChanged ->  {
                val validateResult = _signUpState.value.passwordInput.let { password ->
                    PasswordValidator.validatePasswordMatch(password, event.confirmPwd)
                }
                _signUpState.update { currentState ->
                    currentState.copy(
                        confirmPassword = event.confirmPwd,
                        confirmPasswordError = validateResult.errorMessage
                    )
                }
            }
            is SignupUiEvent.CourseChanged -> courseNameChanged(event.course)
            is SignupUiEvent.EmailChanged -> validateEmail(event.email)
            is SignupUiEvent.FirstNameChanged -> firstNameChanged(event.firstName)
            is SignupUiEvent.LastNameChanged -> lastNameChanged(event.lastname)
            is SignupUiEvent.PasswordChanged -> passwordChanged(event.password)

            is SignupUiEvent.SignUp ->createUserWithEmailAndPassword()
        }
    }

    private fun clearErrors() {
        _signUpState.update { it.copy(
            emailError = null,
            passwordError = null,
            confirmPasswordError = null,
            errorMessage = null
        ) }
    }

    private fun validateEmail(email: String) {
        val validationResult = InputValidators.validateEmail(email)
        _signUpState.update { it.copy(
            emailInput = email,
            emailError = validationResult.errorMessage
        ) }
    }

    private fun courseNameChanged(course: String){
        _signUpState.update { currentState ->
            currentState.copy(courseName = course)
        }
    }
    private fun firstNameChanged(firstName: String) {
        _signUpState.update { currentState ->
            currentState.copy(firstNameInput = firstName)
        }
    }
    private fun lastNameChanged(lastName: String) {
        _signUpState.update { currentState ->
            currentState.copy(lastNameInput = lastName)
        }
    }

    private fun passwordChanged(password: String) {
        val validationResult = PasswordValidator.validatePassword(password)
        _signUpState.update { currentState ->
            currentState.copy(
                passwordInput = password,
                passwordError = validationResult.errorMessage
            )
        }
    }

    private fun isFormValid(): Boolean {
        val emailValidation = _signUpState.value.emailInput.let { InputValidators.validateEmail(it) }
        val passwordValidation = _signUpState.value.passwordInput.let { PasswordValidator.validatePassword(it) }
        val confirmPasswordValidation = _signUpState.value.confirmPassword.let {
            _signUpState.value.passwordInput.let { it1 ->
                PasswordValidator.validatePasswordMatch(
                    it1,
                    it
                )
            }
        }


        _signUpState.update { currentState ->
            currentState.copy(
                emailError = emailValidation.errorMessage,
                passwordError = passwordValidation.errorMessage,
                confirmPasswordError = confirmPasswordValidation.errorMessage,
            )
        }

        return emailValidation.isValid  &&
                passwordValidation.isValid  &&
                confirmPasswordValidation.isValid
    }

    private fun createUserWithEmailAndPassword() {

        if (!isFormValid()) {
            return
        }
        viewModelScope.launch {
//            _signUpState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val signUpRequest = SignupRequest(
                    firstName  = _signUpState.value.firstNameInput,
                    lastName = _signUpState.value.lastNameInput,
                    email = _signUpState.value.emailInput,
                    password = _signUpState.value.passwordInput,
                    course = _signUpState.value.courseName,
                )
                authRepository.createUserWithEmailAndPassword(
                    signUpRequest
                )
                    .collectLatest { result ->
                        when(result) {
                            is Resource.Error -> {
                                _signUpState.value = _signUpState.value.copy(errorMessage = result.message)
                            }
                            is Resource.Loading -> {
                                _signUpState.value = _signUpState.value.copy(isLoading = true)
                            }
                            is Resource.Success -> {
                                _signUpState.value = _signUpState.value.copy(
                                    isLoading = false,
                                    errorMessage = null,
                                    signupResponse = result.data
                                )
                                _navigateToLogin.send(SignUpNavigationEvent.NavigateToLogin)
                            }
                        }
                    }
            } catch (e: Exception) {
                _signUpState.update { it.copy(
                    isLoading = false,
                    errorMessage = e.message
                ) }
            }

        }
    }
}