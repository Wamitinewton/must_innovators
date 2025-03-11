package com.newton.core.domain.models.admin_models

/**
{
  "id": 3,
  "username": "piexie3bett",
  "email": "checktix92@gmail.com",
  "first_name": "Manu",
  "last_name": "Bett",
  "course": "checktix92@gmail.com",
  "photo":""
}
*/
data class UserData(
    val course: String = "",
    val email: String = "",
    val first_name: String = "",
    val id: Int = 0,
    val last_name: String = "",
    val photo: String? = null,
    val username: String = ""
)