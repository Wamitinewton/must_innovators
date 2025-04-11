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
package com.newton.admin.presentation.club.state

import com.newton.network.domain.models.aboutUs.*
import com.newton.network.domain.models.admin.*

data class ClubState(
    val name: String = "",
    val clubDetails: String = "",
    val vision: String = "",
    val mission: String = "",
    val socials: List<Socials> = emptyList(),
    val clubData: ClubBioData? = null,
    val isLoading: Boolean = false,
    val isUpdatedSuccess: Boolean = false,
    val errorMessage: String? = null,
    val errors: Map<String, String> = emptyMap()
)
