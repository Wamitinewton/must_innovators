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
package com.newton.account.presentation.viewmodel

import com.newton.account.presentation.states.*
import com.newton.network.domain.models.authModels.*
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
