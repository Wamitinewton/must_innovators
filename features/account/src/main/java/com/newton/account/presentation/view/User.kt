package com.newton.account.presentation.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color

data class User(
    val username: String,
    val email: String,
    val course: String,
    val yearOfStudy: Int,
    val firstName: String,
    val lastName: String,
    val description: String,
    val profilePictureUrl: String? = null
)


data class Community(
    val id: String,
    val name: String,
    val membersCount: Int,
    val imageUrl: String? = null,
    val color: Color
)

data class Blog(
    val id: String,
    val title: String,
    val preview: String,
    val date: String,
    val readTime: Int,
    val imageUrl: String? = null,
    val likes: Int
)
