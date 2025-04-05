package com.newton.admin.data.mappers

import com.newton.core.data.response.admin.*
import com.newton.core.domain.models.adminModels.*

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
