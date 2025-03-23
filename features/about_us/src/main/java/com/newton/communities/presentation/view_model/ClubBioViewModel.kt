package com.newton.communities.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.communities.presentation.state.ClubBioUiState
import com.newton.core.domain.repositories.CommunityRepository
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubBioViewModel @Inject constructor(
    private val communityRepository: CommunityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ClubBioUiState>(ClubBioUiState.Loading)
    val uiState: StateFlow<ClubBioUiState> = _uiState.asStateFlow()

    init {
        fetchClubBio()
    }

    private fun fetchClubBio() {
        viewModelScope.launch {
            try {
                communityRepository.getClubBio().onEach { result ->
                    when (result) {
                        is Resource.Error -> {
                            _uiState.value = ClubBioUiState.Error(result.message ?: "An unknown error occurred")
                        }

                        is Resource.Loading -> {
                            if (result.isLoading) {
                                _uiState.value = ClubBioUiState.Loading
                            }
                        }

                        is Resource.Success -> {
                            result.data?.let { data ->
                                _uiState.value = ClubBioUiState.Success(data)
                            } ?: run {
                                _uiState.value = ClubBioUiState.Error("Data is null")
                            }
                        }
                    }
                }.launchIn(this)
            } catch (e: Exception) {
                _uiState.value = ClubBioUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}