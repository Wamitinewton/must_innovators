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
package com.newton.account.presentation.states

import com.newton.network.domain.models.authModels.*

data class UpdateProfileState(
    val userData: UserData? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null,
    val isSuccess: Boolean = false
)

data class ProfileFormState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val course: String = "",
    val registrationNo: String = "",
    val bio: String = "",
    val techStacks: List<String> = emptyList(),
    val github: String? = null,
    val linkedin: String? = null,
    val twitter: String? = null,
    val yearOfStudy: Int? = null,
    val graduationYear: Int? = null,
    val projects: List<Project> = emptyList(),
    val skills: List<String> = emptyList()
)
