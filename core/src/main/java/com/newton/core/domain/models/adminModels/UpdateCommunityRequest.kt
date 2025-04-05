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
package com.newton.core.domain.models.adminModels

import com.newton.core.domain.models.admin.*
import com.newton.core.domain.models.admin.Session

data class UpdateCommunityRequest(
    val name: String? = null,
    val communityLead: String? = null,
    val coLead: String? = null,
    val secretary: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val isRecruiting: Boolean? = null,
    val description: String? = null,
    val foundingDate: String? = null,
    val techStack: List<String>? = null,
    val sessions: List<Session>? = null,
    val socialMedia: List<Socials>? = null
)
