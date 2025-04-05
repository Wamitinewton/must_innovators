package com.newton.account.presentation.viewmodel

import com.newton.account.presentation.states.*
import com.newton.core.domain.models.authModels.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@Singleton
class ProfileStateReducer
@Inject
constructor() {
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

    fun handleUpdateSuccess(
        userData: UserData,
        message: String
    ) {
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
