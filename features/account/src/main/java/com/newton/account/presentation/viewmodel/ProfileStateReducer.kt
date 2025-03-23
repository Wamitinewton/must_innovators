package com.newton.account.presentation.viewmodel


import com.newton.account.presentation.states.ProfileViewState
import com.newton.account.presentation.states.UpdateProfileState
import com.newton.core.domain.models.auth_models.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileStateReducer @Inject constructor() {

    private val _accountState = MutableStateFlow(ProfileViewState())
    val accountState: StateFlow<ProfileViewState> = _accountState.asStateFlow()

    private val _updateProfileState = MutableStateFlow(UpdateProfileState())
    val updateProfileState: StateFlow<UpdateProfileState> = _updateProfileState.asStateFlow()

    fun updateUserData(userData: UserData) {
        _accountState.update { it.copy(userData = userData) }
        _updateProfileState.update { it.copy(userData = userData) }
    }

    fun handleUpdateLoading(isLoading: Boolean) {
        _updateProfileState.update {
            it.copy(isLoading = isLoading)
        }
    }

    fun handleUpdateSuccess(userData: UserData, message: String) {
        _updateProfileState.update {
            it.copy(
                userData = userData,
                message = message,
                isSuccess = true,
                isLoading = false,
                error = null
            )
        }
        _accountState.update { it.copy(userData = userData) }
    }

    fun handleUpdateError(error: String) {
        _updateProfileState.update {
            it.copy(
                error = error,
                isSuccess = false,
                isLoading = false,
                message = null
            )
        }
    }

    fun handleUpdateNoChanges() {
        _updateProfileState.update {
            it.copy(
                message = "No changes to save",
                isSuccess = false,
                isLoading = false,
                error = null
            )
        }
    }

    fun handleNoUserData() {
        _updateProfileState.update {
            it.copy(
                error = "No user data available",
                isSuccess = false,
                isLoading = false,
                message = null
            )
        }
    }

    fun clearMessages() {
        _updateProfileState.update {
            it.copy(message = null, error = null, isSuccess = false)
        }
    }
}