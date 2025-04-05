package com.newton.account.presentation.states

import com.newton.core.domain.models.authModels.*

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
    val github: String = "",
    val linkedin: String = "",
    val twitter: String = "",
    val yearOfStudy: Int? = null,
    val graduationYear: Int? = null,
    val projects: List<Project> = emptyList(),
    val skills: List<String> = emptyList()
)
