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
package com.newton.admin.presentation.club.event

import com.newton.network.domain.models.admin.*

sealed class ClubEvent {
    data class NameChanged(val name: String) : ClubEvent()
    data class ClubDetailChanged(val aboutUs: String) : ClubEvent()
    data class VisionChanged(val vision: String) : ClubEvent()
    data class MissionChanged(val mission: String) : ClubEvent()
    data class SocialsChanged(val social: List<Socials>) : ClubEvent()
    data object UpdateClub : ClubEvent()
    data object ToDefault : ClubEvent()
    data object LoadClub : ClubEvent()
}
