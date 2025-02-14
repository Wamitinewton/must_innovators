package com.newton.auth.presentation.login.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.domain.repositories.AuthRepository
import com.newton.auth.presentation.login.event.GetUserDataEvent
import com.newton.auth.presentation.login.state.GetUserDataViewModelState
import com.newton.core.domain.models.GetUserData
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GetUserDataViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    private val _getUserDataState: MutableStateFlow<GetUserDataViewModelState> = MutableStateFlow(
        GetUserDataViewModelState()
    )
    val getUserDataState: StateFlow<GetUserDataViewModelState> get() = _getUserDataState

    init {
        getUserData()
    }



    fun onEvent(event: GetUserDataEvent) {
        when(event) {
            GetUserDataEvent.GetUserEvent -> {
                fetchRemoteUser()
            }
        }
    }

    private fun getUserData() {
        viewModelScope.launch {
            try {
                val localUser = authRepository.getLoggedInUser()
                if (localUser != null) {
                    _getUserDataState.update {
                        it.copy(
                            isLoading = false,
                            userData = localUser
                        )
                    }
                }

                fetchRemoteUser()
            } catch (e: Exception) {
                _getUserDataState.update {
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
                        _getUserDataState.update {
                            it.copy(
                                errorMessage = result.message,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _getUserDataState.update { it.copy(isLoading = result.isLoading) }
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
            _getUserDataState.update {
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
            _getUserDataState.update {
                it.copy(
                    errorMessage = "Failed to get user credentials: ${e.message}",
                    isLoading = false,
                )
            }
            Timber.e(e, "Failed to get User Data!!!")
        }
    }
}