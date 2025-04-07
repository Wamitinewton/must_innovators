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
package com.newton.admin.presentation.partners.events

import java.io.File
import java.time.LocalDate

sealed class AddPartnersEvent {
    data class NameChange(val name: String) : AddPartnersEvent()
    data class TypeChange(val type: String) : AddPartnersEvent()
    data class DescriptionChange(val description: String) : AddPartnersEvent()
    data class WebsiteChange(val website: String) : AddPartnersEvent()
    data class ContactEmailChange(val email: String) : AddPartnersEvent()
    data class ContactPersonChange(val person: String) : AddPartnersEvent()
    data class LinkedInChange(val linkedIn: String) : AddPartnersEvent()
    data class TwitterChange(val twitter: String) : AddPartnersEvent()
    data class StartDateChange(val startDate: LocalDate) : AddPartnersEvent()
    data class EndDateChange(val endDate: LocalDate?) : AddPartnersEvent()
    data class ScopeChange(val scope: String) : AddPartnersEvent()
    data class BenefitsChange(val benefit: String) : AddPartnersEvent()
    data class SupportChange(val support: String) : AddPartnersEvent()
    data class ResourcesChange(val resources: String) : AddPartnersEvent()
    data class AchievementsChange(val achievements: String) : AddPartnersEvent()
    data class AudienceTargetChange(val audience: String) : AddPartnersEvent()
    data class StatusChange(val status: String) : AddPartnersEvent()
    data class LogoChange(val logo: File?) : AddPartnersEvent()
    data class IsPartnerTypeExpanded(val expanded: Boolean) : AddPartnersEvent()
    data class IsStatusExpanded(val expanded: Boolean) : AddPartnersEvent()
    data class ShowStartDate(val shown: Boolean) : AddPartnersEvent()
    data class ShowEndDate(val shown: Boolean) : AddPartnersEvent()
    data class OnGoingPartnership(val ongoing: Boolean) : AddPartnersEvent()
    data object PickImage : AddPartnersEvent()
    data object AddPartners : AddPartnersEvent()
    data object ToDefault : AddPartnersEvent()
}