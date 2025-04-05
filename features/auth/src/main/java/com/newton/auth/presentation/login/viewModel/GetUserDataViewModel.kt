package com.newton.auth.presentation.login.viewModel

import androidx.lifecycle.*
import com.newton.auth.presentation.login.event.*
import com.newton.auth.presentation.login.state.*
import com.newton.core.domain.repositories.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class GetUserDataViewModel
@Inject
constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val stateHolder =
        UserDataStateHolder(
            authRepository = authRepository,
            viewModelScope = viewModelScope
        )

    val getUserDataState: StateFlow<GetUserDataViewModelState> = stateHolder.state

    fun onEvent(event: GetUserDataEvent) {
        when (event) {
            GetUserDataEvent.GetUserEvent -> {
                stateHolder.getUserData()
            }
        }
    }
}
