package com.newton.auth.presentation.login.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.domain.models.get_user.GetUserData
import com.newton.auth.domain.repositories.AuthRepository
import com.newton.auth.presentation.login.event.GetUserDataEvent
import com.newton.auth.presentation.login.state.GetUserDataViewModelState
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class GetUserDataViewModel @Inject constructor(
    private val authRepository: AuthRepository
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
                getUserData()
            }
        }
    }

    private fun getUserData() {
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

    private fun handleGetUserSuccess(getUserData: GetUserData) {
        try {
            _getUserDataState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = null,
                    successMessage = getUserData.message,
                    userData = getUserData.data
                )
            }
        } catch (e: Exception) {
            _getUserDataState.update {
                it.copy(
                    errorMessage = "Failed to save login credentials: ${e.message}",
                    isLoading = false,
                )
            }
            Timber.e(e, "Failed to get User Data!!!")
        }
    }
}