package com.newton.database.mappers

import com.newton.core.domain.models.authModels.*
import com.newton.database.entities.*

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
