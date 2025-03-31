package com.newton.admin.presentation.club.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.admin.presentation.club.event.ClubEvent
import com.newton.admin.presentation.club.state.ClubState
import com.newton.core.domain.models.admin_models.Club
import com.newton.core.domain.repositories.AdminRepository
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ClubViewModel @Inject constructor(
    private val adminRepo: AdminRepository
) : ViewModel() {
    private val _clubState = MutableStateFlow(ClubState())
    val clubState: StateFlow<ClubState> = _clubState.asStateFlow()


    fun handleEvent(event: ClubEvent) {
        when (event) {
            is ClubEvent.ClubDetailChanged -> _clubState.update { it.copy(clubDetails = event.aboutUs) }
            is ClubEvent.MissionChanged -> _clubState.update { it.copy(mission = event.mission) }
            is ClubEvent.NameChanged -> _clubState.update { it.copy(name = event.name) }
            is ClubEvent.SocialsChanged -> _clubState.update { it.copy(socials = event.social) }
            ClubEvent.UpdateClub -> updateClub()
            is ClubEvent.VisionChanged -> _clubState.update { it.copy(vision = event.vision) }
        }
    }


    private fun toDefault() {
        _clubState.value = ClubState()
    }

    private fun validated(): Boolean {
        val errors = mutableMapOf<String, String>()
        if (_clubState.value.name.isBlank()) {
            errors["name"] = "Name of the Club is required"
        }
        if (_clubState.value.mission.isBlank()) {
            errors["mission"] = "Mission of the Club is required"
        }
        if (_clubState.value.clubDetails.isBlank()) {
            errors["aboutus"] = "Detail about the club  is required"
        }
        if (_clubState.value.vision.isBlank()) {
            errors["vision"] = "Vision of the Club is required"
        }
//        if (_clubState.value.socials.isNotEmpty()) {
//            errors["socials"] = "Club socials is required"
//        }
        _clubState.value = _clubState.value.copy(errors = errors)
        return errors.isEmpty()
    }


    private fun updateClub() {
        if(validated()) {
            val club = Club(
                mission = _clubState.value.mission,
                name = _clubState.value.name,
                aboutUs = _clubState.value.clubDetails,
                vision = _clubState.value.vision,
                socialMedia = _clubState.value.socials
            )
            viewModelScope.launch {
                adminRepo.updateClub(club).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _clubState.update { it.copy(errorMessage = result.message) }
                        }

                        is Resource.Loading -> _clubState.update { it.copy(isLoading = result.isLoading) }
                        is Resource.Success -> {
                            _clubState.update {
                                it.copy(
                                    errorMessage = null,
                                    isLoading = false,
                                )
                            }
                            toDefault()
                        }
                    }
                }
            }
        }
    }
}