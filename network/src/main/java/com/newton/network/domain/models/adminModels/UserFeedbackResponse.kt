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
package com.newton.network.domain.models.adminModels

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
