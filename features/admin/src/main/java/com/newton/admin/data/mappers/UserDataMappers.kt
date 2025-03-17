package com.newton.admin.data.mappers

import com.newton.core.domain.models.admin_models.UserData

object UserDataMappers {
    private fun UserData.toDomain():User{
        return User(
            id = id,
            name = "$first_name $last_name",
            email = email,
            photo = photo
        )
    }

    fun List<UserData>.toDomainList():List<User> = map { it.toDomain() }
}