package com.newton.auth.presentation.login.view_model

import com.newton.auth.presentation.login.state.GetUserDataViewModelState
import com.newton.core.domain.models.auth_models.GetUserData
import com.newton.core.domain.repositories.AuthRepository
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

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
                when(result) {
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
                    isLoading = false,
                )
            }
            Timber.e(e, "Failed to get User Data!!!")
        }
    }
}