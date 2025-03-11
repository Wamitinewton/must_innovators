package com.newton.admin.presentation.partners.viewModel

import androidx.lifecycle.ViewModel
import com.newton.core.domain.repositories.AdminRepository
import com.newton.admin.presentation.partners.states.PartnersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PartnersViewModel @Inject constructor(
    private val adminRepository: AdminRepository
): ViewModel() {

    private val _partnersState = MutableStateFlow(PartnersState())
    val partnersState : StateFlow<PartnersState> = _partnersState.asStateFlow()


}