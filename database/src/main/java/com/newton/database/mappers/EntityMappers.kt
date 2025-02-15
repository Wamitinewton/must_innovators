package com.newton.database.mappers

import com.newton.core.domain.models.auth_models.UserData
import com.newton.database.entities.UserEntity

fun UserEntity.toAuthedUser(): UserData {
    return UserData(
        course = course,
        email = email,
        first_name = firstName,
        last_name = firstName,
        username = userName
    )
}

fun UserData.toUserEntity(): UserEntity {
    return UserEntity(
        course = course,
        email = email,
        firstName = first_name,
        lastName = last_name,
        userName = username,
        id = id ?: 1,
    )
}