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
package com.newton.core.domain.models.testimonials

import kotlinx.serialization.*

@Serializable
data class Testimonials(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<TestimonialsData>
)

@Serializable
data class TestimonialsData(
    val content: String,
    val created_at: String,
    val id: Int,
    val rating: Int,
    val status: String,
    val user: Int,
    val user_name: String
)
