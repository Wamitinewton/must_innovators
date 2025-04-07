/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.admin.presentation.club.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.admin.presentation.club.event.ClubEvent
import com.newton.admin.presentation.club.state.ClubState
import com.newton.network.Resource
import com.newton.network.domain.models.adminModels.Club
import com.newton.network.domain.repositories.AdminRepository
import com.newton.network.domain.repositories.CommunityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubViewModel @Inject constructor(
    private val adminRepo: AdminRepository,
    private val communityRepository: CommunityRepository
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
            ClubEvent.LoadClub -> loadClubData()
            ClubEvent.ToDefault -> toDefault()
        }
    }

    init {
        loadClubData()
    }

    private fun loadClubData() {
        viewModelScope.launch {
            communityRepository.getClubBio().collectLatest { result ->
                when (result) {
                    is Resource.Error -> _clubState.update { it.copy(errorMessage = result.message) }
                    is Resource.Loading -> _clubState.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        result.data?.let { club ->
                            _clubState.update { it.copy(clubData = club) }
                        }
                    }
                }
            }
        }
    }


    private fun toDefault() {
        _clubState.value = ClubState()
    }

    private fun updateClub() {
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
                        _clubState.update { it.copy(errorMessage = result.message, isLoading = false) }
                    }

                    is Resource.Loading -> _clubState.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        _clubState.update {
                            it.copy(
                                errorMessage = null,
                                isLoading = false,
                                isUpdatedSuccess = true
                            )
                        }
                    }
                }
            }
        }
    }
}
