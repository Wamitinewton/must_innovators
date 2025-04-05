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
package com.newton.events.presentation.states

import com.newton.core.data.response.admin.*
import com.newton.core.enums.*

data class EventRegistrationState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val course: String = "",
    val educationLevel: String = "1",
    val expectations: String = "",
    val phoneNumberError: String? = null,
    val expectationsError: String? = null,
    val educationLevelError: String? = null,
    val isLoading: Boolean = false,
    val success: RegistrationResponse? = null,
    val errorMessage: String? = null,
    val eventRegistrationFlow: EventRegistrationFlow = EventRegistrationFlow.REGISTER
)
