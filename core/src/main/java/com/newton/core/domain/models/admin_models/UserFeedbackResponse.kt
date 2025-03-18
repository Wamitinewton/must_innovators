package com.newton.core.domain.models.admin_models

data class UserFeedbackResponse(
    val category: String,
    val category_display: String,
    val comment: String,
    val email: String,
    val id: Int,
    val priority: String,
    val priority_display: String,
    val rating: Int,
    val screenshot: String,
    val status: String,
    val status_display: String,
    val submitted_at: String,
    val updated_at: String,
    val user: Int,
    val user_name: String
)