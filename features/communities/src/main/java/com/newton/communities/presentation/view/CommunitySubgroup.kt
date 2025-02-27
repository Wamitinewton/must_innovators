package com.newton.communities.presentation.view

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class CommunitySubgroup(
    val id: String,
    val name: String,
    val description: String,
    val memberCount: Int,
    val icon: ImageVector,
    val color: Color
)

data class ExecutiveMember(
    val id: String,
    val name: String,
    val role: String,
    val photoUrl: String? = null,
    val department: String
)
