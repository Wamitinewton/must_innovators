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
package com.newton.account.presentation.view

import androidx.compose.ui.graphics.*

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
