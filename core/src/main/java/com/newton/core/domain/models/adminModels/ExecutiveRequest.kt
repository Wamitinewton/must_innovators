package com.newton.core.domain.models.adminModels

import com.newton.core.enums.*

data class ExecutiveRequest(
    val userId: Int,
    val position: ExecutivePosition,
    val bio: String
)
