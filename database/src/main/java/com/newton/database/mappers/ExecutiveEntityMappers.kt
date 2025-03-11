package com.newton.database.mappers

import com.newton.core.domain.models.about_us.Executive
import com.newton.database.entities.ExecutiveEntity

fun Executive.toExecutiveEntity(): ExecutiveEntity {
    return ExecutiveEntity(
        id = id,
        name = name,
        position = position,
        bio = bio,
        email = email
    )
}

fun ExecutiveEntity.toExecutiveDomain(): Executive {
    return Executive(
        id = id,
        name = name,
        position = position,
        bio = bio,
        email = email
    )
}