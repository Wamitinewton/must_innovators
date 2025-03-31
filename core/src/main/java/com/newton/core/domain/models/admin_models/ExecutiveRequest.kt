package com.newton.core.domain.models.admin_models

import com.newton.core.enums.ExecutivePosition


data class ExecutiveRequest(
    val userId: Int,
    val position: ExecutivePosition,
    val bio:String,
)
