package com.newton.auth.presentation.login.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.auth.presentation.login.event.GetUserDataEvent
import com.newton.auth.presentation.login.state.GetUserDataViewModelState
import com.newton.core.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GetUserDataViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): ViewModel() {

    private val stateHolder = UserDataStateHolder(
        authRepository = authRepository,
        viewModelScope = viewModelScope
    )

    val getUserDataState: StateFlow<GetUserDataViewModelState> = stateHolder.state

    fun onEvent(event: GetUserDataEvent) {
        when(event) {
            GetUserDataEvent.GetUserEvent -> {
                stateHolder.getUserData()
            }
        }
    }
}