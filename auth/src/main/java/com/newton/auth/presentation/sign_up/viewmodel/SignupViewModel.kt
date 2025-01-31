package com.newton.auth.presentation.sign_up.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.domain.models.sign_up.SignupRequest
import com.newton.auth.domain.repositories.AuthRepository
import com.newton.auth.presentation.sign_up.event.SignupUiEvent
import com.newton.auth.presentation.sign_up.state.SignupViewmodelState
import com.newton.core.utils.InputValidators
import com.newton.core.utils.PasswordValidator
import com.newton.core.utils.RemoteText
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

    private val _navigateToLogin = Channel<Unit>()
    val navigateToLogin = _navigateToLogin.receiveAsFlow()

    private val _signUpState: MutableStateFlow<SignupViewmodelState> = MutableStateFlow(SignupViewmodelState())
    val authUiState: StateFlow<SignupViewmodelState> get() = _signUpState

    fun onEvent(event: SignupUiEvent) {
        when(event) {
            SignupUiEvent.ClearError -> {
                _signUpState.update { currentState ->
                    currentState.copy(
                        emailError = null,
                        passwordError = null,
                        confirmPassword = null,
                        registrationNoError = null,
                        errorMessage = RemoteText.Idle
                    )
                }
            }
            is SignupUiEvent.ConfirmPasswordChanged -> {
                val validateResult = _signUpState.value.passwordInput?.let { password ->
                    PasswordValidator.validatePasswordMatch(password, event.confirmPwd)
                }
                _signUpState.update { currentState ->
                    currentState.copy(
                        confirmPassword = event.confirmPwd,
                        confirmPasswordError = validateResult?.errorMessage
                    )
                }
            }
            is SignupUiEvent.CourseChanged -> {
                _signUpState.update { currentState ->
                    currentState.copy(courseName = event.course)
                }
            }
            is SignupUiEvent.EmailChanged -> {
                val validationResult = InputValidators.validateEmail(event.email)
                _signUpState.update { currentState ->
                    currentState.copy(
                        emailInput = event.email,
                        emailError = validationResult.errorMessage
                    )
                }
            }
            is SignupUiEvent.FirstNameChanged -> {
                _signUpState.update { currentState ->
                    currentState.copy(firstNameInput = event.firstName)
                }
            }
            is SignupUiEvent.LastNameChanged -> {
                _signUpState.update { currentState ->
                    currentState.copy(lastNameInput = event.lastname)
                }
            }
            is SignupUiEvent.PasswordChanged -> {
                val validationResult = PasswordValidator.validatePassword(event.password)
                _signUpState.update { currentState ->
                    currentState.copy(
                        passwordInput = event.password,
                        passwordError = validationResult.errorMessage
                    )
                }
            }
            is SignupUiEvent.RegistrationNoChanged -> {
                val validationResult = InputValidators.validateRegistrationNumber(event.regNo)
                _signUpState.update { currentState ->
                    currentState.copy(
                        registrationNo = event.regNo,
                        registrationNoError = validationResult.errorMessage
                    )
                }
            }
            is SignupUiEvent.SignUp -> {
                createUserWithEmailAndPassword()
            }
            is SignupUiEvent.UsernameChanged -> {
                _signUpState.update { currentState ->
                    currentState.copy(userName = event.username)
                }
            }
        }
    }

    private fun isFormValid(): Boolean {
        val emailValidation = _signUpState.value.emailInput?.let { InputValidators.validateEmail(it) }
        val passwordValidation = _signUpState.value.passwordInput?.let { PasswordValidator.validatePassword(it) }
        val confirmPasswordValidation = _signUpState.value.confirmPassword?.let {
            _signUpState.value.passwordInput?.let { it1 ->
                PasswordValidator.validatePasswordMatch(
                    it1,
                    it
                )
            }
        }
        val regNoValidation = _signUpState.value.registrationNo?.let {
            InputValidators.validateRegistrationNumber(
                it
            )
        }

        _signUpState.update { currentState ->
            currentState.copy(
                emailError = emailValidation?.errorMessage,
                passwordError = passwordValidation?.errorMessage,
                confirmPasswordError = confirmPasswordValidation?.errorMessage,
                registrationNoError = regNoValidation?.errorMessage
            )
        }

        return emailValidation?.isValid == true &&
                passwordValidation?.isValid == true &&
                confirmPasswordValidation?.isValid == true &&
                regNoValidation?.isValid == true
    }

    private fun createUserWithEmailAndPassword() {
        val firstName = _signUpState.value.firstNameInput
        val lastName = _signUpState.value.lastNameInput
        val email = _signUpState.value.emailInput
        val userName = _signUpState.value.userName
        val registrationNo = _signUpState.value.registrationNo
        val courseName = _signUpState.value.courseName
        val password = _signUpState.value.passwordInput

        if (!isFormValid()) {
            return
        }
        viewModelScope.launch {
            authRepository.createUserWithEmailAndPassword(
                SignupRequest(
                    firstName!!,
                    lastName!!,
                    email!!,
                    userName!!,
                    password!!,
                    registrationNo,
                    courseName
                )
            )
                .collectLatest { result ->
                    when(result) {
                        is Resource.Error -> {
                            _signUpState.value = _signUpState.value.copy(errorMessage = RemoteText.RemoteString(result.message.toString()))
                        }
                        is Resource.Loading -> {
                            _signUpState.value = _signUpState.value.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            _signUpState.value = _signUpState.value.copy(
                                isLoading = false,
                                errorMessage = RemoteText.Idle,
                                signupResponse = result.data
                            )
                            _navigateToLogin.send(Unit)
                        }
                    }
                }
        }
    }
}