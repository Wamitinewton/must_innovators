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
package com.newton.network.domain.models.aboutUs

data class ExecutiveData(
    val data: List<Executive>,
    val message: String,
    val status: String
)

data class Executive(
    val community: Int,
    val community_details: CommunityDetails,
    val id: Int,
    val joined_date: String,
    val position: String,
    val user: Int,
    val user_details: ExecutiveDetails
)

data class CommunityDetails(
    val id: Int,
    val name: String
)

data class ExecutiveDetails(
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val username: String
)
