package com.newton.home.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.domain.models.home_models.PartnersData
import com.newton.core.domain.repositories.HomeRepository
import com.newton.core.utils.Resource
import com.newton.home.presentation.states.PartnersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartnersViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _partnersState = MutableStateFlow<PartnersUiState>(PartnersUiState.Loading)
    val partnersState: StateFlow<PartnersUiState> = _partnersState.asStateFlow()

    init {
        loadPartners()
    }

    private fun loadPartners() {
        viewModelScope.launch {
            _partnersState.value = PartnersUiState.Loading

            homeRepository.getPartners().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        if (result.isLoading) {
                            _partnersState.value = PartnersUiState.Loading
                        }
                    }

                    is Resource.Success -> {
                        val partners = result.data ?: emptyList()
                        _partnersState.value = if (partners.isEmpty()) {
                            PartnersUiState.Empty
                        } else {
                            PartnersUiState.Success(partners)
                        }
                    }

                    is Resource.Error -> {
                        _partnersState.value = PartnersUiState.Error(
                            result.message ?: "Something went wrong"
                        )
                    }
                }
            }
        }
    }

    fun refreshPartners() {
        loadPartners()
    }
}

@HiltViewModel
class PartnersSharedViewModel @Inject constructor(
): ViewModel() {

    var selectedPartner by mutableStateOf<PartnersData?>(null)
        private set


    fun updateSelectedPartner(partnersData: PartnersData) {
        selectedPartner = partnersData
    }

    fun clearSelectedEvent() {
        selectedPartner = null
    }
}