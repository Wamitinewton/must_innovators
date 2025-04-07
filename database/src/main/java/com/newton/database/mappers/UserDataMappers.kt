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
package com.newton.database.mappers

import com.newton.database.entities.*
import com.newton.network.domain.models.authModels.*

fun UserEntity.toAuthedUser(): UserData {
    return UserData(
        id = id,
        username = userName,
        email = email,
        first_name = firstName,
        last_name = lastName,
        course = course,
        registration_no = registrationNo,
        bio = bio,
        tech_stacks = techStacks,
        social_media = socialMedia,
        photo = photo,
        year_of_study = yearOfStudy,
        graduation_year = graduationYear,
        projects = projects,
        skills = skills
    )
}

fun UserData.toUserEntity(): UserEntity {
    return UserEntity(
        id = id ?: 1,
        userName = username,
        email = email,
        firstName = first_name,
        lastName = last_name,
        course = course,
        registrationNo = registration_no,
        bio = bio,
        techStacks = tech_stacks,
        socialMedia = social_media,
        photo = photo,
        yearOfStudy = year_of_study,
        graduationYear = graduation_year,
        projects = projects,
        skills = skills
    )
}
