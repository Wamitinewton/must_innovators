package com.newton.core.domain.models.admin_models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
{
  "uid": "df7a49c0-e7a2-434b-a6c4-a9686f60cecf",
  "full_name": "newton wamiti",
  "email": "newtondev461@gmail.com",
  "course": "bcs",
  "educational_level": "2",
  "phone_number": "0792036343",
  "expectations": "I am expecting a new job for the inconvenience",
  "registration_timestamp": "2025-02-22T07:32:34.637172Z",
  "ticket_number": "9efe4af6-e9ac-4da0-96f1-318bd97db2e9",
  "event": 17
}
*/
@Serializable
data class Attendees(
    @SerialName("uid")
    val uid: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("email")
    val email: String,
    @SerialName("course")
    val course: String,
    @SerialName("educational_level")
    val educationalLevel: String,
    @SerialName("phone_number")
    val phoneNumber: String,
    @SerialName("expectations")
    val expectations: String,
    @SerialName("registration_timestamp")
    val registrationTimestamp: String,
    @SerialName("ticket_number")
    val ticketNumber: String,
    @SerialName("event")
    val event: Int
)