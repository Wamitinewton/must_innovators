package com.newton.account.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.account.presentation.states.ProfileFormState
import com.newton.account.presentation.states.ProfileViewState
import com.newton.account.presentation.states.UpdateProfileState
import com.newton.core.domain.models.auth_models.Project
import com.newton.core.domain.repositories.AuthRepository
import com.newton.core.domain.repositories.UpdateUserRepository
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UpdateAccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val updateUserRepository: UpdateUserRepository,
    private val profileStateManager: ProfileStateHolder,
    private val profileStateReducer: ProfileStateReducer
) : ViewModel() {

    val accountState: StateFlow<ProfileViewState> = profileStateReducer.accountState
    val profileState: StateFlow<UpdateProfileState> = profileStateReducer.updateProfileState
    val profileFormState: StateFlow<ProfileFormState> = profileStateManager.profileFormState

    init {
        observeUserData()
    }

    private fun observeUserData() {
        authRepository.observeLoggedInUser()
            .onEach { userData ->
                userData?.let {
                    profileStateReducer.updateUserData(it)
                    profileStateManager.updateFormWithUserData(it)
                } ?: Timber.w("No user data found")
            }
            .catch { e ->
                Timber.e(e, "Error observing user data")
            }
            .launchIn(viewModelScope)
    }

    fun updateFirstName(firstName: String) = profileStateManager.updateFirstName(firstName)
    fun updateLastName(lastName: String) = profileStateManager.updateLastName(lastName)
    fun updateCourse(course: String) = profileStateManager.updateCourse(course)
    fun updateRegistrationNo(registrationNo: String) = profileStateManager.updateRegistrationNo(registrationNo)
    fun updateBio(bio: String) = profileStateManager.updateBio(bio)
    fun updateGithub(github: String) = profileStateManager.updateGithub(github)
    fun updateLinkedin(linkedin: String) = profileStateManager.updateLinkedin(linkedin)
    fun updateTwitter(twitter: String) = profileStateManager.updateTwitter(twitter)
    fun updateYearOfStudy(yearOfStudy: Int?) = profileStateManager.updateYearOfStudy(yearOfStudy)
    fun updateGraduationYear(graduationYear: Int?) = profileStateManager.updateGraduationYear(graduationYear)
    fun updateProjects(projects: List<Project>) = profileStateManager.updateProjects(projects)
    fun addProject(project: Project) = profileStateManager.addProject(project)
    fun removeProject(index: Int) = profileStateManager.removeProject(index)
    fun addSkill(skill: String) = profileStateManager.addSkill(skill)
    fun removeSkill(skill: String) = profileStateManager.removeSkill(skill)
    fun addTechStack(techStack: String) = profileStateManager.addTechStack(techStack)
    fun removeTechStack(techStack: String) = profileStateManager.removeTechStack(techStack)

    fun updateProfile() {
        val currentUserData = accountState.value.userData ?: run {
            Timber.e("Cannot update profile: No user data available")
            profileStateReducer.handleNoUserData()
            return
        }

        val updateRequest = profileStateManager.createUpdateRequest(currentUserData)
        Timber.d("Update request created: $updateRequest")

        if (profileStateManager.isEmptyUpdateRequest(updateRequest)) {
            Timber.d("No changes detected in the form")
            profileStateReducer.handleUpdateNoChanges()
            return
        }

        Timber.d("Submitting profile update")
        viewModelScope.launch {
            updateUserRepository.updateUserProfile(updateRequest)
                .catch { e ->
                    Timber.e(e, "Error during profile update")
                    profileStateReducer.handleUpdateError("Failed to update profile: ${e.message}")
                }
                .onEach { result ->
                    Timber.d("Update profile result: $result")
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { response ->
                                profileStateReducer.handleUpdateSuccess(response.data, response.message)
                                profileStateManager.updateFormWithUserData(response.data)
                            }
                        }
                        is Resource.Error -> {
                            profileStateReducer.handleUpdateError(
                                result.message ?: "An unknown error occurred"
                            )
                        }
                        is Resource.Loading -> {

                            profileStateReducer.handleUpdateLoading(result.isLoading)

                        }
                    }
                }
                .launchIn(this)
        }
    }

    fun clearMessages() = profileStateReducer.clearMessages()

}