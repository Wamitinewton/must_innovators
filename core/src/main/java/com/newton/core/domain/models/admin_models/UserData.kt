package com.newton.core.domain.models.admin_models

/**
{
    "id": 13,
    "username": "stephen8865",
    "email": "stephenondeyo0@gmail.com",
    "first_name": "Stephen",
    "last_name": "Ondeyo",
    "course": "BCS"
  }
*/
data class UserData(
    val course: String = "",
    val email: String = "",
    val first_name: String = "",
    val id: Int = 0,
    val last_name: String = "",
    val username: String = ""
)