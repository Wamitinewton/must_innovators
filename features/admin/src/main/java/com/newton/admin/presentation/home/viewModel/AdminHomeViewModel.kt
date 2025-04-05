package com.newton.admin.presentation.home.viewModel

import androidx.lifecycle.*
import com.newton.admin.presentation.home.events.*
import com.newton.admin.presentation.home.states.*
import kotlinx.coroutines.flow.*

class AdminHomeViewModel : ViewModel() {
    private val _adminState = MutableStateFlow(AdminHomeState())
    val adminState: StateFlow<AdminHomeState> = _adminState.asStateFlow()

    fun handleEvents(event: AdminHomeEvent) {
        when (event) {
            is AdminHomeEvent.Sheet -> _adminState.update { it.copy(isShowChoices = event.shown) }
        }
    }
}
