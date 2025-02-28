package com.newton.admin.presentation.home.viewModel

import androidx.lifecycle.ViewModel
import com.newton.admin.presentation.home.events.AdminHomeEvent
import com.newton.admin.presentation.home.states.AdminHomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AdminHomeViewModel : ViewModel() {
    private val _adminState = MutableStateFlow(AdminHomeState())
    val adminState: StateFlow<AdminHomeState> = _adminState.asStateFlow()

    fun handleEvents(event:AdminHomeEvent){
        when (event){
            is AdminHomeEvent.Sheet -> _adminState.update { it.copy(isShowChoices = event.shown) }
        }
    }
}