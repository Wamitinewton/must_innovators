package com.newton.admin.presentation.partners.viewModel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.admin.presentation.partners.events.AddPartnersEvent
import com.newton.admin.presentation.partners.states.AddPartnersEffect
import com.newton.core.domain.repositories.AdminRepository
import com.newton.admin.presentation.partners.states.AddPartnersState
import com.newton.common_ui.ui.toStringLocalTime
import com.newton.core.domain.models.admin_models.AddPartnerRequest
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartnersViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {

    private val _addPartnersState = MutableStateFlow(AddPartnersState())
    val addPartnersState: StateFlow<AddPartnersState> = _addPartnersState.asStateFlow()

    private val _uiSideEffect = MutableSharedFlow<AddPartnersEffect>()
    val uiSideEffect get() = _uiSideEffect.asSharedFlow()


    fun handleEvent(event: AddPartnersEvent) {
        when (event) {
            is AddPartnersEvent.AchievementsChange -> _addPartnersState.update {
                it.copy(
                    achievements = event.achievements
                )
            }

            is AddPartnersEvent.AudienceTargetChange -> _addPartnersState.update {
                it.copy(
                    targetAudience = event.audience
                )
            }

            is AddPartnersEvent.BenefitsChange -> _addPartnersState.update { it.copy(keyBenefits = event.benefit) }
            is AddPartnersEvent.ContactEmailChange -> _addPartnersState.update {
                it.copy(
                    contactEmail = event.email
                )
            }

            is AddPartnersEvent.ContactPersonChange -> _addPartnersState.update {
                it.copy(
                    contactPerson = event.person
                )
            }

            is AddPartnersEvent.DescriptionChange -> _addPartnersState.update { it.copy(description = event.description) }
            is AddPartnersEvent.EndDateChange -> _addPartnersState.update {
                it.copy(
                    partnershipEndDate = event.endDate
                )
            }

            is AddPartnersEvent.LinkedInChange -> _addPartnersState.update { it.copy(socialLinkedIn = event.linkedIn) }
            is AddPartnersEvent.LogoChange -> _addPartnersState.update { it.copy(partnershipLogo = event.logo) }
            is AddPartnersEvent.NameChange -> _addPartnersState.update { it.copy(partnerName = event.name) }
            is AddPartnersEvent.ResourcesChange -> _addPartnersState.update {
                it.copy(
                    resourcesProvided = event.resources
                )
            }

            is AddPartnersEvent.ScopeChange -> _addPartnersState.update { it.copy(collaborationScope = event.scope) }
            is AddPartnersEvent.StartDateChange -> _addPartnersState.update {
                it.copy(
                    partnershipStartDate = event.startDate
                )
            }

            is AddPartnersEvent.StatusChange -> _addPartnersState.update { it.copy(status = event.status) }
            is AddPartnersEvent.SupportChange -> _addPartnersState.update { it.copy(eventsSupported = event.support) }
            is AddPartnersEvent.TwitterChange -> _addPartnersState.update { it.copy(socialTwitter = event.twitter) }
            is AddPartnersEvent.TypeChange -> _addPartnersState.update { it.copy(partnerType = event.type) }
            is AddPartnersEvent.WebsiteChange -> _addPartnersState.update { it.copy(website = event.website) }
            is AddPartnersEvent.IsPartnerTypeExpanded -> _addPartnersState.update {
                it.copy(
                    partnerTypeExpanded = event.expanded
                )
            }

            is AddPartnersEvent.IsStatusExpanded -> _addPartnersState.update {
                it.copy(
                    statusExpanded = event.expanded
                )
            }

            is AddPartnersEvent.ShowEndDate -> _addPartnersState.update { it.copy(showEndDatePicker = event.shown) }
            is AddPartnersEvent.ShowStartDate -> _addPartnersState.update {
                it.copy(
                    showStartDatePicker = event.shown
                )
            }

            AddPartnersEvent.AddPartners -> addPartner()
            is AddPartnersEvent.OnGoingPartnership -> _addPartnersState.update {
                it.copy(
                    ongoingPartnership = event.ongoing
                )
            }

            AddPartnersEvent.PickImage -> emit(AddPartnersEffect.PickImage)
        }
    }

    private fun emit(effect: AddPartnersEffect) = viewModelScope.launch {
        _uiSideEffect.emit(effect)
    }

    private fun toDefault() {
        _addPartnersState.value = AddPartnersState()
    }

    private fun validateAndSubmit(): Boolean {
        val errors = mutableMapOf<String, String>()
        if (_addPartnersState.value.partnerName.isBlank()) {
            errors["name"] = "Partners name is required"
        }
        if (_addPartnersState.value.partnerType.isBlank()) {
            errors["partnerType"] = "Type of partnership is required"
        }
        if (_addPartnersState.value.partnershipLogo == null) {
            errors["logo"] = "The Logo of the Partner is required"
        }
        if (_addPartnersState.value.description.isBlank()) {
            errors["description"] = "Partner details is required"
        }
        if (_addPartnersState.value.website.isBlank()) {
            errors["webUrl"] = "Partners website link is required"
        }
        if (_addPartnersState.value.collaborationScope.isBlank()) {
            errors["scope"] = "The scope of partnership is required is required"
        }
        if (_addPartnersState.value.status.isBlank()) {
            errors["status"] = "Partners status  is required"
        }
        if (_addPartnersState.value.contactPerson.isBlank()) {
            errors["person"] = "Partner's Contact person is required"
        }
        if (_addPartnersState.value.keyBenefits.isBlank()) {
            errors["benefit"] = "It's required to explain 0ur benefits from the partnership"
        }

        if (!_addPartnersState.value.ongoingPartnership && _addPartnersState.value.partnershipEndDate == null) {
            errors["endDate"] = "End date of Partnership  is required"
        }


        if (_addPartnersState.value.contactEmail.isBlank()) {
            errors["email"] = "Contact email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(_addPartnersState.value.contactEmail)
                .matches()
        ) {
            errors["email"] = "Invalid email format"
        }


        _addPartnersState.value = _addPartnersState.value.copy(errors = errors)
        return errors.isEmpty()
    }

    private fun addPartner() {
        if (validateAndSubmit()) {
            val partner = AddPartnerRequest(
                name = _addPartnersState.value.partnerName,
                type = _addPartnersState.value.partnerType,
                description = _addPartnersState.value.description,
                logo = _addPartnersState.value.partnershipLogo!!,
                webUrl = _addPartnersState.value.website,
                contactEmail = _addPartnersState.value.contactEmail,
                contactPerson = _addPartnersState.value.contactPerson,
                linkedIn = _addPartnersState.value.socialLinkedIn,
                twitter = _addPartnersState.value.socialTwitter,
                startDate = _addPartnersState.value.partnershipStartDate.toStringLocalTime(),
                endDate = _addPartnersState.value.partnershipEndDate?.toStringLocalTime() ?: "null",
                ongoing = _addPartnersState.value.ongoingPartnership,
                status = _addPartnersState.value.status,
                scope = _addPartnersState.value.collaborationScope,
                benefits = _addPartnersState.value.keyBenefits,
                eventsSupported = _addPartnersState.value.eventsSupported,
                resources = _addPartnersState.value.resourcesProvided,
                achievements = _addPartnersState.value.achievements,
                targetAudience = _addPartnersState.value.targetAudience
            )
            viewModelScope.launch {
                adminRepository.addPartner(partner).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _addPartnersState.update { it.copy(errorMessage = result.message) }
                        }

                        is Resource.Loading -> _addPartnersState.update { it.copy(isLoading = result.isLoading) }
                        is Resource.Success -> {
                            _addPartnersState.update {
                                it.copy(
                                    errorMessage = null,
                                    isLoading = false,
                                    isSuccess = true,
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