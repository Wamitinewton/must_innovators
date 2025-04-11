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
package com.newton.admin.presentation.community.viewmodels

import androidx.lifecycle.*
import com.newton.admin.presentation.community.events.*
import com.newton.admin.presentation.community.states.*
import com.newton.network.*
import com.newton.network.domain.models.adminModels.*
import com.newton.network.domain.repositories.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class UpdateCommunityViewModel
@Inject
constructor(
    private val adminRepository: AdminRepository,
    private val communityRepo: CommunityRepository
) : ViewModel() {
    private val _updateCommunityState = MutableStateFlow(UpdateCommunityState())
    val updateCommunityState: StateFlow<UpdateCommunityState> = _updateCommunityState.asStateFlow()

    init {
        loadCommunities()
    }

    fun handleEvents(event: UpdateCommunityEvent) {
        when (event) {
            is UpdateCommunityEvent.CoLeadChanged -> _updateCommunityState.update { it.copy(coLead = event.coLead) }
            is UpdateCommunityEvent.CurrentRoleSelectionChange ->
                _updateCommunityState.update {
                    it.copy(currentRoleSelection = event.currentRole)
                }

            is UpdateCommunityEvent.DateForFieldChange ->
                _updateCommunityState.update {
                    it.copy(dateForField = event.dateForField)
                }

            is UpdateCommunityEvent.DateFounded ->
                _updateCommunityState.update {
                    it.copy(
                        dateFounded = event.date
                    )
                }

            is UpdateCommunityEvent.DateFoundedChanged ->
                _updateCommunityState.update {
                    it.copy(
                        dateFounded = event.date
                    )
                }

            is UpdateCommunityEvent.DescriptionChanged ->
                _updateCommunityState.update {
                    it.copy(
                        description = event.description
                    )
                }

            is UpdateCommunityEvent.EmailCHanged -> _updateCommunityState.update { it.copy(email = event.email) }
            is UpdateCommunityEvent.IsEditingChange ->
                _updateCommunityState.update {
                    it.copy(
                        isEditing = event.editing
                    )
                }

            is UpdateCommunityEvent.IsRecruitingChanged ->
                _updateCommunityState.update {
                    it.copy(
                        isRecruiting = event.recruiting
                    )
                }

            is UpdateCommunityEvent.LeadChanged -> _updateCommunityState.update { it.copy(lead = event.lead) }
            is UpdateCommunityEvent.LoadUsers -> TODO()
            is UpdateCommunityEvent.NameChanged -> _updateCommunityState.update { it.copy(name = event.name) }
            is UpdateCommunityEvent.PhoneChanged -> _updateCommunityState.update { it.copy(phone = event.phone) }
            is UpdateCommunityEvent.SecretaryChanged ->
                _updateCommunityState.update {
                    it.copy(
                        secretary = event.secretary
                    )
                }

            is UpdateCommunityEvent.SessionDate ->
                _updateCommunityState.update {
                    it.copy(
                        sessionDate = event.date
                    )
                }

            is UpdateCommunityEvent.SessionToEdit ->
                _updateCommunityState.update {
                    it.copy(
                        sessionToEdit = event.session
                    )
                }

            is UpdateCommunityEvent.SessionsChanged ->
                _updateCommunityState.update {
                    it.copy(
                        sessions = event.session
                    )
                }

            is UpdateCommunityEvent.ShowAddSession ->
                _updateCommunityState.update {
                    it.copy(
                        showAddSessionDialog = event.shown
                    )
                }

            is UpdateCommunityEvent.ShowAddSocialDialog ->
                _updateCommunityState.update {
                    it.copy(showAddSocialDialog = event.shown)
                }

            is UpdateCommunityEvent.ShowBottomSheet ->
                _updateCommunityState.update {
                    it.copy(
                        showBottomSheet = event.shown
                    )
                }

            is UpdateCommunityEvent.ShowDatePicker ->
                _updateCommunityState.update {
                    it.copy(
                        showDatePicker = event.shown
                    )
                }

            is UpdateCommunityEvent.SocialToEditChange ->
                _updateCommunityState.update {
                    it.copy(
                        socialToEdit = event.social
                    )
                }

            is UpdateCommunityEvent.SocialsChanged ->
                _updateCommunityState.update {
                    it.copy(
                        socials = event.socials
                    )
                }

            UpdateCommunityEvent.ToDefault -> toDefault()
            is UpdateCommunityEvent.ToolsChanged ->
                _updateCommunityState.update {
                    it.copy(
                        toolsText = event.tools
                    )
                }

            is UpdateCommunityEvent.UpdateCommunity -> updateCommunity(event.communityId)
        }
    }

    private fun loadCommunities() {
        viewModelScope.launch {
            communityRepo.getCommunities().collectLatest { result ->
                when (result) {
                    is Resource.Error -> _updateCommunityState.update { it.copy(errorMessage = result.message) }
                    is Resource.Loading -> _updateCommunityState.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        result.data?.let { community ->
                            _updateCommunityState.update { it.copy(communities = community) }
                        }
                    }
                }
            }
        }
    }

    private fun toDefault() {
        _updateCommunityState.value = UpdateCommunityState()
    }

    private fun updateCommunity(communityId: Int) {
        val request =
            UpdateCommunityRequest(
                name = _updateCommunityState.value.name,
                communityLead = _updateCommunityState.value.lead,
                coLead = _updateCommunityState.value.coLead,
                secretary = _updateCommunityState.value.secretary,
                email = _updateCommunityState.value.email,
                phoneNumber = _updateCommunityState.value.phone,
                isRecruiting = _updateCommunityState.value.isRecruiting,
                description = _updateCommunityState.value.description,
                foundingDate = _updateCommunityState.value.dateFounded,
                techStack =
                _updateCommunityState.value.toolsText?.split(
                    ","
                )?.map { it.trim() }?.filter { it.isNotEmpty() },
                sessions = _updateCommunityState.value.sessions,
                socialMedia = _updateCommunityState.value.socials
            )
        viewModelScope.launch {
            adminRepository.updateCommunity(communityId, request).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _updateCommunityState.value = _updateCommunityState.value.copy(
                            uploadError = result.message ?: "Unknown error when adding community"
                        )
                    }

                    is Resource.Loading -> {
                        _updateCommunityState.value =
                            _updateCommunityState.value.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        _updateCommunityState.value =
                            _updateCommunityState.value.copy(
                                uploadSuccess = true,
                                isLoading = false,
                                uploadError = null
                            )
                    }
                }
            }
        }
    }
}
