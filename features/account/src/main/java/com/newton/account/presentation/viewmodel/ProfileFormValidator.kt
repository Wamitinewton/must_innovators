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

import com.newton.network.domain.models.authModels.*
import javax.inject.*

@Singleton
class ProfileFormValidator
@Inject
constructor() {
    fun validateProject(project: Project): Boolean = project.name.isNotBlank() && project.description.isNotBlank()

    fun validateSkill(skill: String): Boolean = skill.isNotBlank()

    fun validateTechStack(techStack: String): Boolean = techStack.isNotBlank()

    fun validateEmail(email: String): Boolean = email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun validateName(name: String): Boolean = name.isNotBlank() && name.length <= 50

    fun validateBio(bio: String): Boolean = bio.length <= 500

    fun validateRegistrationNo(registrationNo: String): Boolean = registrationNo.isBlank() || registrationNo.matches(Regex("^[A-Za-z0-9-]+$"))

    fun validateYearOfStudy(year: Int?): Boolean = year == null || (year in 1..7)

    fun validateGraduationYear(year: Int?): Boolean = year == null || (year in 2024..2030)
}
