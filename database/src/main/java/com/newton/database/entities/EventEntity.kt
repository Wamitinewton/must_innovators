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

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: Int,
    val imageUrl: String,
    val name: String,
    val category: String,
    val description: String,
    val date: String,
    val location: String,
    val organizer: String,
    val contactEmail: String,
    val isVirtual: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val pageNumber: Int? = null
)

@Entity(tableName = "events_pagination_metadata")
data class EventPaginationMetadata(
    @PrimaryKey
    val pageNumber: Int,
    val nextPageUrl: String?,
    val timestamp: Long
)
