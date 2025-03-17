package com.newton.account.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.account.presentation.states.ProfileFormState
import com.newton.account.presentation.states.ProfileViewState
import com.newton.account.presentation.states.UpdateProfileState
import com.newton.core.domain.models.auth_models.Project
import com.newton.core.domain.models.auth_models.SocialMedia
import com.newton.core.domain.models.auth_models.UpdateProfileRequest
import com.newton.core.domain.models.auth_models.UserData
import com.newton.core.domain.repositories.AuthRepository
import com.newton.core.domain.repositories.UpdateUserRepository
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UpdateAccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val updateUserRepository: UpdateUserRepository
) : ViewModel() {

    private val _accountState = MutableStateFlow(ProfileViewState())
    val accountState: StateFlow<ProfileViewState> = _accountState.asStateFlow()

    private val _updateProfileState = MutableStateFlow(UpdateProfileState())
    val profileState: StateFlow<UpdateProfileState> = _updateProfileState.asStateFlow()

    private val _profileFormState = MutableStateFlow(ProfileFormState())
    val profileFormState: StateFlow<ProfileFormState> = _profileFormState.asStateFlow()

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            try {
                val localUser = authRepository.getLoggedInUser()
                Timber.d("Retrieved user data: $localUser")

                if (localUser != null) {
                    _accountState.update { it.copy(userData = localUser) }
                    _updateProfileState.update { it.copy(userData = localUser) }
                    updateFormWithUserData(localUser)
                } else {
                    Timber.w("No user data found")
                }
            } catch (e: Exception) {
                Timber.e(e, "Error retrieving user data")
            }
        }
    }

    private fun updateFormWithUserData(userData: UserData) {
        _profileFormState.value = ProfileFormState(
            firstName = userData.first_name,
            lastName = userData.last_name,
            email = userData.email,
            course = userData.course ?: "",
            registrationNo = userData.registration_no ?: "",
            bio = userData.bio ?: "",
            techStacks = userData.tech_stacks ?: emptyList(),
            github = userData.social_media?.github ?: "",
            linkedin = userData.social_media?.linkedin ?: "",
            twitter = userData.social_media?.twitter ?: "",
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
    fun updateRegistrationNo(registrationNo: String) = updateField { it.copy(registrationNo = registrationNo) }
    fun updateBio(bio: String) = updateField { it.copy(bio = bio) }
    fun updateGithub(github: String) = updateField { it.copy(github = github) }
    fun updateLinkedin(linkedin: String) = updateField { it.copy(linkedin = linkedin) }
    fun updateTwitter(twitter: String) = updateField { it.copy(twitter = twitter) }
    fun updateYearOfStudy(yearOfStudy: Int?) = updateField { it.copy(yearOfStudy = yearOfStudy) }
    fun updateGraduationYear(graduationYear: Int?) = updateField { it.copy(graduationYear = graduationYear) }
    fun updateProjects(projects: List<Project>) = updateField { it.copy(projects = projects) }

    fun addProject(project: Project) {
        if (project.name.isBlank() || project.description.isBlank()) return

        updateField { state ->
            val updatedProjects = state.projects.toMutableList().apply { add(project) }
            state.copy(projects = updatedProjects)
        }
    }

    fun removeProject(index: Int) = updateField { state ->
        val updatedProjects = state.projects.toMutableList().apply {
            if (index in indices) removeAt(index)
        }
        state.copy(projects = updatedProjects)
    }

    fun addSkill(skill: String) {
        if (skill.isBlank()) return

        updateField { state ->
            val currentSkills = state.skills.toMutableList()
            if (!currentSkills.contains(skill)) {
                currentSkills.add(skill)
                state.copy(skills = currentSkills)
            } else state
        }
    }

    fun removeSkill(skill: String) = updateField { state ->
        val updatedSkills = state.skills.toMutableList().apply { remove(skill) }
        state.copy(skills = updatedSkills)
    }

    fun addTechStack(techStack: String) {
        if (techStack.isBlank()) return

        updateField { state ->
            val currentTechStacks = state.techStacks.toMutableList()
            if (!currentTechStacks.contains(techStack)) {
                currentTechStacks.add(techStack)
                state.copy(techStacks = currentTechStacks)
            } else state
        }
    }

    fun removeTechStack(techStack: String) = updateField { state ->
        val updatedTechStacks = state.techStacks.toMutableList().apply { remove(techStack) }
        state.copy(techStacks = updatedTechStacks)
    }

    fun updateProfile() {
        val formState = _profileFormState.value
        val currentUserData = _accountState.value.userData ?: run {
            Timber.e("Cannot update profile: No user data available")
            _updateProfileState.update {
                it.copy(
                    error = "No user data available",
                    isSuccess = false,
                    isLoading = false
                )
            }
            return
        }

        val updateRequest = createUpdateRequest(formState, currentUserData)

        Timber.d("Update request created: $updateRequest")

        if (isEmptyUpdateRequest(updateRequest)) {
            Timber.d("No changes detected in the form")
            _updateProfileState.update {
                it.copy(
                    message = "No changes to save",
                    isSuccess = false,
                    isLoading = false
                )
            }
            return
        }

        Timber.d("Submitting profile update")
        viewModelScope.launch {
            updateUserRepository.updateUserProfile(updateRequest)
                .catch { e ->
                    Timber.e(e, "Error during profile update")
                    _updateProfileState.update {
                        it.copy(
                            error = "Failed to update profile: ${e.message}",
                            isSuccess = false,
                            isLoading = false
                        )
                    }
                }
                .onEach { result ->
                    Timber.d("Update profile result: $result")
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { response ->
                                _updateProfileState.update {
                                    it.copy(
                                        userData = response.data,
                                        message = response.message,
                                        isSuccess = true,
                                        isLoading = false
                                    )
                                }
                                _accountState.update { it.copy(userData = response.data) }
                                updateFormWithUserData(response.data)
                            }
                        }
                        is Resource.Error -> {
                            _updateProfileState.update {
                                it.copy(
                                    error = result.message ?: "An unknown error occurred",
                                    isSuccess = false,
                                    isLoading = false
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _updateProfileState.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun isEmptyUpdateRequest(request: UpdateProfileRequest): Boolean {
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

    private fun createUpdateRequest(
        formState: ProfileFormState,
        currentUserData: UserData
    ): UpdateProfileRequest {
        val socialMedia = SocialMedia(
            github = formState.github.takeIf { it != currentUserData.social_media?.github },
            linkedin = formState.linkedin.takeIf { it != currentUserData.social_media?.linkedin },
            twitter = formState.twitter.takeIf { it != currentUserData.social_media?.twitter }
        )

        val socialMediaToInclude = if (
            socialMedia.github != null ||
            socialMedia.linkedin != null ||
            socialMedia.twitter != null
        ) socialMedia else null

        return UpdateProfileRequest(
            first_name = formState.firstName.takeIf { it != currentUserData.first_name },
            last_name = formState.lastName.takeIf { it != currentUserData.last_name },
            email = formState.email.takeIf { it != currentUserData.email },
            course = formState.course.takeIf { it.isNotBlank() && it != currentUserData.course },
            registration_no = formState.registrationNo.takeIf { it.isNotBlank() && it != currentUserData.registration_no },
            bio = formState.bio.takeIf { it.isNotBlank() && it != currentUserData.bio },
            tech_stacks = formState.techStacks.takeIf { it != currentUserData.tech_stacks },
            social_media = socialMediaToInclude,
            year_of_study = formState.yearOfStudy.takeIf { it != currentUserData.year_of_study },
            graduation_year = formState.graduationYear.takeIf { it != currentUserData.graduation_year },
            projects = formState.projects.takeIf { it != currentUserData.projects },
            skills = formState.skills.takeIf { it != currentUserData.skills }
        )
    }

    fun clearMessages() {
        _updateProfileState.update {
            it.copy(message = null, error = null, isSuccess = false)
        }
    }

    private fun deleteAccount() {

    }
}