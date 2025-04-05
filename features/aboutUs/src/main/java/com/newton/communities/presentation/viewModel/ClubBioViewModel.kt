package com.newton.communities.presentation.viewModel

import androidx.lifecycle.*
import com.newton.communities.presentation.state.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class ClubBioViewModel
@Inject
constructor(
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
                            _uiState.value =
                                ClubBioUiState.Error(result.message ?: "An unknown error occurred")
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
