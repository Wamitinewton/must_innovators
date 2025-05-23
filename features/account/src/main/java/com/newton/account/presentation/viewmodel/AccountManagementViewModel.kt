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

import androidx.lifecycle.*
import com.newton.account.presentation.events.*
import com.newton.account.presentation.states.*
import com.newton.network.*
import com.newton.network.domain.repositories.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import timber.log.*
import javax.inject.*

@HiltViewModel
class AccountManagementViewModel
@Inject
constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _navigateToAccountDeleted = Channel<DeleteAccountNavigationEvent>()
    val navigateToAccountDeleted = _navigateToAccountDeleted.receiveAsFlow()

    private val _deleteAccountState: MutableStateFlow<DeleteAccountState> =
        MutableStateFlow(DeleteAccountState())
    val deleteAccountState: StateFlow<DeleteAccountState> get() = _deleteAccountState

    private val _logoutState = MutableStateFlow(LogoutState())
    val logoutState: StateFlow<LogoutState> get() = _logoutState

    private val _navigateAfterLogout = Channel<LogoutNavigationEvent>()
    val navigateAfterLogout = _navigateAfterLogout.receiveAsFlow()

    fun onDeleteAccountEvent(event: DeleteAccountEvent) {
        when (event) {
            DeleteAccountEvent.DeleteAccount -> {
                deleteAccount()
            }

            DeleteAccountEvent.ClearError -> {
                _deleteAccountState.update { it.copy(errorMessage = null) }
            }
        }
    }

    fun onLogoutEvent(event: LogoutEvent) {
        when (event) {
            LogoutEvent.Logout -> {
                logoutUser()
            }

            LogoutEvent.ClearError -> {
                _logoutState.update { it.copy(errorMessage = null) }
            }
        }
    }

    private fun logoutUser() {
        viewModelScope.launch {
            _logoutState.update { it.copy(isLoading = true) }

            authRepository.logoutUser()
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _logoutState.update {
                                it.copy(
                                    errorMessage = result.message,
                                    isLoading = false
                                )
                            }
                            Timber.e("Failed to logout: ${result.message}")
                        }

                        is Resource.Loading -> {
                            _logoutState.update { it.copy(isLoading = result.isLoading) }
                        }

                        is Resource.Success -> {
                            _logoutState.update {
                                it.copy(
                                    isLoggedOut = true,
                                    isLoading = false,
                                    errorMessage = null
                                )
                            }
                            _navigateAfterLogout.send(LogoutNavigationEvent.NavigateToLogin)
                            Timber.d("User logged out successfully")
                        }
                    }
                }
        }
    }

    private fun deleteAccount() {
        viewModelScope.launch {
            authRepository.deleteAccount()
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _deleteAccountState.update { it.copy(errorMessage = "Failed to delete account") }
                        }

                        is Resource.Loading -> {
                            _deleteAccountState.update { it.copy(isLoading = result.isLoading) }
                        }

                        is Resource.Success -> {
                            handleAccountDeletionSuccess()
                        }
                    }
                }
        }
    }

    private suspend fun handleAccountDeletionSuccess() {
        try {
            authRepository.clearUserData()

            _deleteAccountState.update {
                it.copy(
                    isDeleted = true,
                    isLoading = false,
                    errorMessage = null
                )
            }
            _navigateToAccountDeleted.send(DeleteAccountNavigationEvent.NavigateToAccountDeleted)
        } catch (e: Exception) {
            _deleteAccountState.update {
                it.copy(
                    errorMessage = "Failed to complete account deletion: ${e.message}",
                    isLoading = false
                )
            }

            Timber.e(e, "Failed to process account deletion")
        }
    }
}
