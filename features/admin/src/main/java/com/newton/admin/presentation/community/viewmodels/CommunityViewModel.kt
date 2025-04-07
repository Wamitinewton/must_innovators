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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.admin.data.mappers.User
import com.newton.admin.data.mappers.UserDataMappers.toDomainList
import com.newton.admin.presentation.community.events.CommunityEvent
import com.newton.admin.presentation.community.states.CommunityState
import com.newton.admin.presentation.community.states.UsersState
import com.newton.network.Resource
import com.newton.network.domain.models.adminModels.AddCommunityRequest
import com.newton.network.domain.repositories.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: AdminRepository
) : ViewModel() {

    private val _communityState = MutableStateFlow(CommunityState())
    val communityState: StateFlow<CommunityState> = _communityState.asStateFlow()

    private val _usersState = MutableStateFlow(UsersState())
    val userState: StateFlow<UsersState> = _usersState.asStateFlow()

    fun handleEvent(event: CommunityEvent) {
        when (event) {
            is CommunityEvent.CoLeadChanged -> _communityState.update { it.copy(coLead = event.coLead) }
            is CommunityEvent.DateFoundedChanged -> _communityState.update { it.copy(dateFounded = event.date) }
            is CommunityEvent.DescriptionChanged -> _communityState.update { it.copy(description = event.description) }
            is CommunityEvent.EmailCHanged -> _communityState.update { it.copy(email = event.email) }
            is CommunityEvent.IsRecruitingChanged -> _communityState.update { it.copy(isRecruiting = event.recruiting) }
            is CommunityEvent.LeadChanged -> _communityState.update { it.copy(lead = event.lead) }
            is CommunityEvent.NameChanged -> _communityState.update { it.copy(name = event.name) }
            is CommunityEvent.PhoneChanged -> _communityState.update { it.copy(phone = event.phone) }
            is CommunityEvent.SecretaryChanged -> _communityState.update { it.copy(secretary = event.secretary) }
            is CommunityEvent.SessionsChanged -> _communityState.update { it.copy(sessions = event.session) }
            is CommunityEvent.SocialsChanged -> _communityState.update { it.copy(socials = event.socials) }
            is CommunityEvent.ToolsChanged -> _communityState.update { it.copy(toolsText = event.tools) }
            is CommunityEvent.CurrentRoleSelectionChange -> _communityState.update {
                it.copy(
                    currentRoleSelection = event.currentRole
                )
            }

            is CommunityEvent.DateForFieldChange -> _communityState.update { it.copy(dateFounded = event.dateForField) }
            is CommunityEvent.DateFounded -> _communityState.update { it.copy(dateFounded = event.date) }
            is CommunityEvent.SessionDate -> _communityState.update { it.copy(sessionDate = event.date) }
            is CommunityEvent.SessionToEdit -> _communityState.update { it.copy(sessionToEdit = event.session) }
            is CommunityEvent.ShowAddSession -> _communityState.update {
                it.copy(
                    showAddSessionDialog = event.shown
                )
            }

            is CommunityEvent.ShowAddSocialDialog -> _communityState.update {
                it.copy(
                    showAddSocialDialog = event.shown
                )
            }

            is CommunityEvent.ShowBottomSheet -> _communityState.update { it.copy(showBottomSheet = event.shown) }
            is CommunityEvent.ShowDatePicker -> _communityState.update { it.copy(showDatePicker = event.shown) }
            is CommunityEvent.SocialToEditChange -> _communityState.update { it.copy(socialToEdit = event.social) }
            CommunityEvent.AddCommunity -> addCommunity()
            is CommunityEvent.LoadUsers -> getAllUsers(event.isRefresh)
            is CommunityEvent.CoLeadIdChanged -> _communityState.update { it.copy(coLeadId = event.coLeadId) }
            is CommunityEvent.LeadIdChanged -> _communityState.update { it.copy(leadId = event.leadId) }
            is CommunityEvent.SecretaryIdChanged -> _communityState.update { it.copy(secretaryId = event.secretaryId) }
            is CommunityEvent.SearchQuery -> _usersState.update { it.copy(searchQuery = event.query) }
            CommunityEvent.ToDefault -> toDefault()
        }
    }

    private fun toDefault() {
        _communityState.value = CommunityState()
        _usersState.value = UsersState()
    }

    private fun getAllUsers(isRefresh: Boolean) {
        viewModelScope.launch {
            repository.getAllUsers(isRefresh).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        result.message?.let { error ->
                            _usersState.update { it.copy(getUsersError = error) }
                        }
                    }

                    is Resource.Loading -> {
                        _usersState.update { it.copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        val userData = result.data?.toDomainList()
                        userData?.let { users ->
                            _usersState.update {
                                it.copy(
                                    isLoading = false,
                                    getUsersError = null,
                                    users = users
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    private fun validate(): Boolean {
        val errors = mutableMapOf<String, String>()
        if (_communityState.value.name.isBlank()) {
            errors["name"] = "Community name is required"
        }
        if (_communityState.value.sessions .isEmpty()) {
            errors["sessions"] = "Assign sessions to your community"
        }
        if (_communityState.value.leadId == null) {
            errors["lead"] = "Lead is required in the community"
        }
        if (_communityState.value.coLeadId==null) {
            errors["colead"] = "Co-lead cannot be null int the community"
        }

        if (_communityState.value.secretaryId ==null) {
            errors["secretary"] = "Secretary of the community  is required"
        }

        if (_communityState.value.description.isBlank()) {
            errors["description"] = "Description of the community is required"
        }

        if (_communityState.value.dateFounded.isBlank()) {
            errors["date"] = "Date founded is required"
        }

        _communityState.value = _communityState.value.copy(errors = errors)
        return errors.isEmpty()
    }



    private fun addCommunity() {
        if (validate()){
            val community = AddCommunityRequest(
                name = _communityState.value.name,
                community_lead = _communityState.value.leadId!!,
                co_lead = _communityState.value.coLeadId!!,
                secretary = _communityState.value.secretaryId!!,
                email = _communityState.value.email,
                phone_number = _communityState.value.phone,
                is_recruiting = _communityState.value.isRecruiting,
                description = _communityState.value.description,
                founding_date = _communityState.value.dateFounded,
                tech_stack = _communityState.value.toolsText.split(",").map { it.trim() }
                    .filter { it.isNotEmpty() },
                sessions = _communityState.value.sessions,
                social_media = _communityState.value.socials
            )
            viewModelScope.launch {
                repository.addCommunity(community).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _communityState.value = _communityState.value.copy(
                                uploadError = result.message ?: "Unknown error when adding community"
                            )
                        }

                        is Resource.Loading -> {
                            _communityState.value =
                                _communityState.value.copy(isLoading = result.isLoading)
                        }

                        is Resource.Success -> {
                            _communityState.value = _communityState.value.copy(
                                isLoading = false,
                                uploadError = null,
                                isSuccess = true
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateCommunity() {
        if (validate()) {
            val community = AddCommunityRequest(
                name = _communityState.value.name,
                community_lead = _communityState.value.leadId!!,
                co_lead = _communityState.value.coLeadId!!,
                secretary = _communityState.value.secretaryId!!,
                email = _communityState.value.email,
                phone_number = _communityState.value.phone,
                is_recruiting = _communityState.value.isRecruiting,
                description = _communityState.value.description,
                founding_date = _communityState.value.dateFounded,
                tech_stack = _communityState.value.toolsText.split(",").map { it.trim() }
                    .filter { it.isNotEmpty() },
                sessions = _communityState.value.sessions,
                social_media = _communityState.value.socials
            )
            viewModelScope.launch {
                repository.addCommunity(community).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _communityState.value =
                                _communityState.value.copy(uploadError = result.message)
                        }

                        is Resource.Loading -> {
                            _communityState.value =
                                _communityState.value.copy(isLoading = result.isLoading)
                        }

                        is Resource.Success -> {
                            _communityState.value = _communityState.value.copy(
                                isLoading = false,
                                uploadError = null,
                            )
                        }
                    }
                }
            }
        }
    }

    fun getSearchedUser(): List<User> {
        val query = _usersState.value.searchQuery.lowercase()
        return _usersState.value.users.filter { user ->
            val matchesSearch = query.isEmpty() ||
                    user.name.lowercase().contains(query) ||
                    user.email.lowercase().contains(query)
            matchesSearch
        }
    }
}