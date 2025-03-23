package com.newton.core.domain.models.admin_models

data class Attendee(
    val course: String,
    val year:String,
    val email: String,
    val event: Int,
    val expectations: String,
    val fullName: String,
    val phone: String,
    val dateRegistered: String,
    val ticketNumber: String,
    val uid: String
)