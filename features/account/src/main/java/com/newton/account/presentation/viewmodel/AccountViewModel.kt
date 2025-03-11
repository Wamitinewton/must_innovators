package com.newton.account.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.account.presentation.states.ProfileViewState
import com.newton.core.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _accountState = MutableStateFlow(ProfileViewState())
    val accountState: StateFlow<ProfileViewState> = _accountState

    init {
        getUserData()
    }



    private fun getUserData() {
        viewModelScope.launch {
                val localUser = authRepository.getLoggedInUser()
                if (localUser != null) {
                    _accountState.update {
                        it.copy(
                            userData = localUser
                        )
                    }
            }
        }
    }

}