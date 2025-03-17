package com.newton.account.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.account.presentation.events.DeleteAccountEvent
import com.newton.account.presentation.events.DeleteAccountNavigationEvent
import com.newton.account.presentation.states.DeleteAccountState
import com.newton.core.domain.repositories.AuthRepository
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountManagementViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _navigateToAccountDeleted = Channel<DeleteAccountNavigationEvent>()
    val navigateToAccountDeleted = _navigateToAccountDeleted.receiveAsFlow()

    private val _deleteAccountState: MutableStateFlow<DeleteAccountState> =
        MutableStateFlow(DeleteAccountState())
    val deleteAccountState: StateFlow<DeleteAccountState> get() = _deleteAccountState

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


    private fun deleteAccount() {
        viewModelScope.launch {
            authRepository.deleteAccount()
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _deleteAccountState.update {
                                it.copy(
                                    errorMessage = result.message,
                                    isLoading = false
                                )
                            }
                            Timber.e("Failed to delete account: ${result.message}")
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