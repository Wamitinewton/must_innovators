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

@Entity(tableName = "my_partners")
data class PartnerEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val description: String,
    val logo: String,
    val webUrl: String,
    val contactEmail: String,
    val contactPerson: String,
    val linkedIn: String,
    val twitter: String,
    val startDate: String,
    val endDate: String,
    val ongoing: Boolean,
    val status: String,
    val scope: String,
    val benefits: String,
    val eventsSupported: String,
    val resources: String,
    val achievements: String,
    val targetAudience: String
)
