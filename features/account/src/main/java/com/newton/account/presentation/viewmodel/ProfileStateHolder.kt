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
package com.newton.account.presentation.viewmodel

import com.newton.account.presentation.states.*
import com.newton.network.domain.models.authModels.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@Singleton
class ProfileStateHolder
@Inject
constructor(
    private val profileValidator: ProfileFormValidator
) {
    private val _profileFormState = MutableStateFlow(ProfileFormState())
    val profileFormState: StateFlow<ProfileFormState> = _profileFormState.asStateFlow()

    fun updateFormWithUserData(userData: UserData) {
        _profileFormState.value =
            ProfileFormState(
                firstName = userData.first_name,
                lastName = userData.last_name,
                email = userData.email,
                course = userData.course ?: "",
                registrationNo = userData.registration_no ?: "",
                bio = userData.bio ?: "",
                techStacks = userData.tech_stacks ?: emptyList(),
                github = userData.social_media?.github,
                linkedin = userData.social_media?.linkedin,
                twitter = userData.social_media?.twitter,
                yearOfStudy = userData.year_of_study,
                graduationYear = userData.graduation_year,
                projects = userData.projects ?: emptyList(),
                skills = userData.skills ?: emptyList()
            )
    }

    private fun updateField(update: (ProfileFormState) -> ProfileFormState) {
        _profileFormState.update { update(it) }
    }

    fun updateFirstName(firstName: String) = updateField { it.copy(firstName = firstName) }

    fun updateLastName(lastName: String) = updateField { it.copy(lastName = lastName) }

    fun updateCourse(course: String) = updateField { it.copy(course = course) }

    fun updateRegistrationNo(registrationNo: String) =
        updateField { it.copy(registrationNo = registrationNo) }

    fun updateBio(bio: String) = updateField { it.copy(bio = bio) }

    fun updateGithub(github: String) = updateField { it.copy(github = github) }

    fun updateLinkedin(linkedin: String) = updateField { it.copy(linkedin = linkedin) }

    fun updateTwitter(twitter: String) = updateField { it.copy(twitter = twitter) }

    fun updateYearOfStudy(yearOfStudy: Int?) = updateField { it.copy(yearOfStudy = yearOfStudy) }

    fun updateGraduationYear(graduationYear: Int?) =
        updateField { it.copy(graduationYear = graduationYear) }

    fun updateProjects(projects: List<Project>) = updateField { it.copy(projects = projects) }

    fun addProject(project: Project) {
        if (!profileValidator.validateProject(project)) return

        updateField { state ->
            val updatedProjects = state.projects.toMutableList().apply { add(project) }
            state.copy(projects = updatedProjects)
        }
    }

    fun removeProject(index: Int) =
        updateField { state ->
            val updatedProjects =
                state.projects.toMutableList().apply {
                    if (index in indices) removeAt(index)
                }
            state.copy(projects = updatedProjects)
        }

    fun addSkill(skill: String) {
        if (!profileValidator.validateSkill(skill)) return

        updateField { state ->
            val currentSkills = state.skills.toMutableList()
            if (!currentSkills.contains(skill)) {
                currentSkills.add(skill)
                state.copy(skills = currentSkills)
            } else {
                state
            }
        }
    }

    fun removeSkill(skill: String) =
        updateField { state ->
            val updatedSkills = state.skills.toMutableList().apply { remove(skill) }
            state.copy(skills = updatedSkills)
        }

    fun addTechStack(techStack: String) {
        if (!profileValidator.validateTechStack(techStack)) return

        updateField { state ->
            val currentTechStacks = state.techStacks.toMutableList()
            if (!currentTechStacks.contains(techStack)) {
                currentTechStacks.add(techStack)
                state.copy(techStacks = currentTechStacks)
            } else {
                state
            }
        }
    }

    fun removeTechStack(techStack: String) =
        updateField { state ->
            val updatedTechStacks = state.techStacks.toMutableList().apply { remove(techStack) }
            state.copy(techStacks = updatedTechStacks)
        }

    fun createUpdateRequest(currentUserData: UserData): UpdateProfileRequest {
        val formState = _profileFormState.value

        val socialMedia =
            SocialMedia(
                github = formState.github
                    ?.takeIf { it.isNotBlank() && it != currentUserData.social_media?.github },
                linkedin = formState.linkedin
                    ?.takeIf { it.isNotBlank() && it != currentUserData.social_media?.linkedin },
                twitter = formState.twitter
                    ?.takeIf { it.isNotBlank() && it != currentUserData.social_media?.twitter }
            )


        val socialMediaToInclude =
            if (
                socialMedia.github != null ||
                socialMedia.linkedin != null ||
                socialMedia.twitter != null
            ) {
                socialMedia
            } else {
                null
            }

        return UpdateProfileRequest(
            first_name = formState.firstName,
            last_name = formState.lastName,
            email = formState.email,
            course = formState.course,
            registration_no = formState.registrationNo,
            bio = formState.bio,
            tech_stacks = formState.techStacks,
            social_media = socialMediaToInclude,
            year_of_study = formState.yearOfStudy,
            graduation_year = formState.graduationYear,
            projects = formState.projects,
            skills = formState.skills
        )
    }

    fun isEmptyUpdateRequest(request: UpdateProfileRequest): Boolean {
        return request.first_name == null &&
            request.last_name == null &&
            request.email == null &&
            request.course == null &&
            request.registration_no == null &&
            request.bio == null &&
            request.tech_stacks == null &&
            request.social_media == null &&
            request.year_of_study == null &&
            request.graduation_year == null &&
            request.projects == null &&
            request.skills == null
    }
}
