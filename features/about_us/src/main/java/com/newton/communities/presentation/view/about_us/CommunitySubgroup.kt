package com.newton.communities.presentation.view.about_us

data class ExecutiveMember(
    val id: String,
    val name: String,
    val role: String,
    val photoUrl: String? = null,
    val department: String
)
