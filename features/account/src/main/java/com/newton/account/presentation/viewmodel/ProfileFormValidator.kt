package com.newton.account.presentation.viewmodel

import com.newton.core.domain.models.auth_models.Project
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileFormValidator @Inject constructor() {

    fun validateProject(project: Project): Boolean {
        return project.name.isNotBlank() && project.description.isNotBlank()
    }

    fun validateSkill(skill: String): Boolean {
        return skill.isNotBlank()
    }

    fun validateTechStack(techStack: String): Boolean {
        return techStack.isNotBlank()
    }

    fun validateEmail(email: String): Boolean {
        return email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validateName(name: String): Boolean {
        return name.isNotBlank() && name.length <= 50
    }

    fun validateBio(bio: String): Boolean {
        return bio.length <= 500
    }

    fun validateRegistrationNo(registrationNo: String): Boolean {
        return registrationNo.isBlank() || registrationNo.matches(Regex("^[A-Za-z0-9-]+$"))
    }

    fun validateYearOfStudy(year: Int?): Boolean {
        return year == null || (year in 1..7)
    }

    fun validateGraduationYear(year: Int?): Boolean {
        return year == null || (year in 2024..2030)
    }
}