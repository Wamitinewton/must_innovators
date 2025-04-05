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
package com.newton.auth.presentation.login.viewModel

import com.newton.auth.presentation.login.state.*
import com.newton.core.domain.models.authModels.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.*

/**
 * StateHolder class responsible for managing the user data state
 */
class UserDataStateHolder(
    private val authRepository: AuthRepository,
    private val viewModelScope: kotlinx.coroutines.CoroutineScope
) {
    private val _state = MutableStateFlow(GetUserDataViewModelState())
    val state: StateFlow<GetUserDataViewModelState> = _state

    init {
        getUserData()
    }

    fun getUserData() {
        viewModelScope.launch {
            try {
                fetchRemoteUser()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Failed to fetch user data",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun fetchRemoteUser() {
        viewModelScope.launch {
            authRepository.getUserData().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                errorMessage = result.message,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        result.data?.let { handleGetUserSuccess(it) }
                    }
                }
            }
        }
    }

    private suspend fun handleGetUserSuccess(getUserData: GetUserData) {
        try {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = null,
                    successMessage = getUserData.message,
                    userData = getUserData.data
                )
            }
            val user = getUserData.data
            authRepository.storeLoggedInUser(user)
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    errorMessage = "Failed to get user credentials: ${e.message}",
                    isLoading = false
                )
            }
            Timber.e(e, "Failed to get User Data!!!")
        }
    }
}
