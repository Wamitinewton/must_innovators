package com.newton.admin.presentation.community.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.newton.admin.domain.models.AddCommunityRequest
import com.newton.admin.domain.repository.AdminRepository
import com.newton.admin.presentation.community.events.CommunityEvent
import com.newton.admin.presentation.community.states.CommunityState
import com.newton.admin.presentation.events.events.AddEventEvents
import com.newton.core.utils.Resource
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

    fun handleEvent(event: CommunityEvent) {
        when (event){
            CommunityEvent.AddCommunity -> addCommunity()
            is CommunityEvent.CoLeadChanged -> _communityState.update { it.copy(coLead = event.coLead) }
            is CommunityEvent.DateFoundedChanged -> _communityState.update { it.copy(dateFounded = event.date)}
            is CommunityEvent.DescriptionChanged -> _communityState.update { it.copy(description = event.description)}
            is CommunityEvent.EmailCHanged -> _communityState.update { it.copy(email = event.email)}
            is CommunityEvent.IsRecruitingChanged -> _communityState.update { it.copy(isRecruiting = event.recruiting)}
            is CommunityEvent.LeadChanged -> _communityState.update { it.copy(lead = event.lead)}
            is CommunityEvent.NameChanged -> _communityState.update { it.copy(name = event.name)}
            is CommunityEvent.PhoneChanged -> _communityState.update { it.copy(phone = event.phone)}
            is CommunityEvent.SecretaryChanged -> _communityState.update { it.copy(secretary = event.secretary)}
            is CommunityEvent.SessionsChanged -> _communityState.update { it.copy(sessions = event.session)}
            is CommunityEvent.SocialsChanged -> _communityState.update { it.copy(socials = event.socials)}
            is CommunityEvent.ToolsChanged -> _communityState.update { it.copy(toolsText = event.tools)}
        }
    }


    private fun addCommunity(){
        val community = AddCommunityRequest(
            name = _communityState.value.name,
            lead =_communityState.value.lead,
            coLead = _communityState.value.coLead,
            secretary = _communityState.value.secretary,
            email = _communityState.value.email,
            phone = _communityState.value.phone,
            isRecruiting = _communityState.value.isRecruiting,
            description = _communityState.value.description,
            dateFounded = _communityState.value.dateFounded,
            tools = _communityState.value.toolsText.split(",").map { it.trim() }.filter { it.isNotEmpty()},
            sessions = _communityState.value.sessions,
            socials = _communityState.value.socials
        )
        viewModelScope.launch {
            repository.addCommunity(community).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _communityState.value = _communityState.value.copy(uploadError = result.message)
                    }

                    is Resource.Loading -> {
                        _communityState.value = _communityState.value.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        _communityState.value = _communityState.value.copy(
                            uploadSuccess = true,
                            isLoading = false,
                            uploadError = null,
                        )
                    }
                }
            }
        }
    }
}