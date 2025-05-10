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
package com.newton.admin.data.mappers

import com.newton.network.data.dto.admin.*
import com.newton.network.domain.models.adminModels.*

object AttendeeMapper {
    fun AttendeeResponse.toAttendee(): Attendee {
        return Attendee(
            course,
            educational_level,
            email,
            event,
            expectations,
            full_name,
            phone_number,
            registration_timestamp,
            ticket_number,
            uid
        )
    }

    fun List<AttendeeResponse>.toAttendeeList(): List<Attendee> = map { it.toAttendee() }
}
