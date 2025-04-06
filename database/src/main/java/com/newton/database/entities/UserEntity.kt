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
package com.newton.database.entities

import androidx.room.*
import com.newton.core.domain.models.authModels.*

@Entity(tableName = "user")
data class UserEntity(
    val course: String? = null,
    val email: String,
    val firstName: String,
    val lastName: String,
    val userName: String,
    val registrationNo: String? = null,
    val bio: String? = null,
    val techStacks: List<String>? = null,
    val socialMedia: com.newton.network.domain.models.authModels.SocialMedia? = null,
    val photo: String? = null,
    val yearOfStudy: Int? = null,
    val graduationYear: Int? = null,
    val projects: List<com.newton.network.domain.models.authModels.Project>? = null,
    val skills: List<String>? = null,
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1
)
