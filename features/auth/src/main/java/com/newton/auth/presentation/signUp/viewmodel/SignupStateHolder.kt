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

import com.newton.auth.presentation.signUp.event.*
import com.newton.auth.presentation.signUp.state.*
import com.newton.core.enums.*
import com.newton.network.domain.models.authModels.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class SignupStateHolder
@Inject
constructor(
    private val validator: SignupFormValidator
) {
    private val _signUpState = MutableStateFlow(SignupViewmodelState())
    val signUpState: StateFlow<SignupViewmodelState> = _signUpState

    fun updateState(event: SignupUiEvent) {
        when (event) {
            SignupUiEvent.ClearError -> clearErrors()
            is SignupUiEvent.ConfirmPasswordChanged -> confirmPasswordChanged(event.confirmPwd)
            is SignupUiEvent.UsernameChanged -> userNameChanged(event.username)
            is SignupUiEvent.CourseChanged -> courseNameChanged(event.course)
            is SignupUiEvent.EmailChanged -> emailChanged(event.email)
            is SignupUiEvent.FirstNameChanged -> firstNameChanged(event.firstName)
            is SignupUiEvent.LastNameChanged -> lastNameChanged(event.lastname)
            is SignupUiEvent.PasswordChanged -> passwordChanged(event.password,event.email)
            is SignupUiEvent.OtpChanged -> updateOtp(event.otp)
            is SignupUiEvent.OnCheckedChanged -> _signUpState.update { it.copy(isChecked = event.checked) }
            else -> {}
        }
    }

    private fun clearErrors() {
        _signUpState.update {
            it.copy(
                emailError = null,
                passwordError = null,
                confirmPasswordError = null,
                errorMessage = null
            )
        }
    }

    private fun updateOtp(otp: String) {
        _signUpState.update { it.copy(otp = otp, otpError = null) }
    }

    private fun emailChanged(email: String) {
        val validationResult = validator.validateEmail(email)
        _signUpState.update {
            it.copy(
                emailInput = email,
                emailError = validationResult.errorMessage
            )
        }
    }

    private fun courseNameChanged(course: String) {
        val courseError = validator.validateCourse(course)
        _signUpState.update { currentState ->
            currentState.copy(courseName = course, courseError = courseError.errorMessage)
        }
    }

    private fun firstNameChanged(firstName: String) {
        val firstNameError = validator.validateName(firstName)
        _signUpState.update { currentState ->
            currentState.copy(firstNameInput = firstName, firstNameError = firstNameError.errorMessage)
        }
    }

    private fun lastNameChanged(lastName: String) {
        val lastnameError = validator.validateName(lastName)
        _signUpState.update { currentState ->
            currentState.copy(lastNameInput = lastName, lastNameError = lastnameError.errorMessage)
        }
    }

    private fun userNameChanged(userName: String) {
        val error = validator.validateUsername(userName)
        _signUpState.update { currentState ->
            currentState.copy(
                userName = userName,
                usernameError = error.errorMessage
            )
        }
    }

    private fun passwordChanged(password: String,email: String) {
        val validationResult = validator.validatePassword(password,email)
        _signUpState.update { currentState ->
            currentState.copy(
                passwordInput = password,
                passwordError = validationResult.errorMessage
            )
        }
    }

    private fun confirmPasswordChanged(confirmPassword: String) {
        val validateResult =
            validator.validatePasswordMatch(
                _signUpState.value.passwordInput,
                confirmPassword
            )
        _signUpState.update { currentState ->
            currentState.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = validateResult.errorMessage
            )
        }
    }

    fun isFormValid(): Boolean {
        val emailValidation = validator.validateEmail(_signUpState.value.emailInput)
        val passwordValidation = validator.validatePassword(_signUpState.value.passwordInput, _signUpState.value.emailInput)
        val confirmPasswordValidation =
            validator.validatePasswordMatch(
                _signUpState.value.passwordInput,
                _signUpState.value.confirmPassword
            )

        _signUpState.update { currentState ->
            currentState.copy(
                emailError = emailValidation.errorMessage,
                passwordError = passwordValidation.errorMessage,
                confirmPasswordError = confirmPasswordValidation.errorMessage
            )
        }
        val usernameValidation = validator.validateUsername(_signUpState.value.userName)
        val courseValidation = validator.validateCourse(_signUpState.value.courseName)
        val firstNameValidation = validator.validateName(_signUpState.value.firstNameInput)
        val lastNameValidation = validator.validateName(_signUpState.value.lastNameInput)

        return emailValidation.isValid &&
            passwordValidation.isValid &&
            confirmPasswordValidation.isValid &&
            usernameValidation.isValid &&
            courseValidation.isValid &&
            firstNameValidation.isValid &&
            lastNameValidation.isValid
    }

    fun validateOtp(otp: String): Boolean {
        val result = validator.validateOtp(otp)
        if (!result.isValid) {
            _signUpState.update { it.copy(otpError = result.errorMessage) }
        }
        return result.isValid
    }

    fun setLoading(isLoading: Boolean) {
        _signUpState.update { it.copy(isLoading = isLoading) }
    }

    fun setError(message: String?) {
        _signUpState.update { it.copy(errorMessage = message) }
    }

    fun setOtpServerError(message: String?) {
        _signUpState.update {
            it.copy(
                otpServerError = message,
                otpError = if (message != null) "Invalid OTP" else null
            )
        }
    }

    fun setSuccess(
        message: String?,
        authFlow: AuthFlow
    ) {
        _signUpState.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                success = message,
                authFlow = authFlow
            )
        }
    }

    fun getSignupRequest(): SignupRequest {
        return SignupRequest(
            firstname = _signUpState.value.firstNameInput,
            lastname = _signUpState.value.lastNameInput,
            email = _signUpState.value.emailInput,
            password = _signUpState.value.passwordInput,
            course = _signUpState.value.courseName,
            username = _signUpState.value.userName
        )
    }

    fun getVerifyOtpRequest(): VerifyOtp {
        return VerifyOtp(
            otp_code = _signUpState.value.otp,
            email = _signUpState.value.emailInput
        )
    }
}
