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
package com.newton.database.db

import androidx.room.*
import com.newton.database.converters.*
import com.newton.database.dao.*
import com.newton.database.entities.*

@Database(
    entities = [
        UserEntity::class,
        EventEntity::class,
        TicketsEntity::class,
        UserFeedbackEntity::class,
        PartnersDataEntity::class,
        EventsFeedbackEntity::class,
        PartnerEntity::class,
        ClubBioEntity::class,
        EventPaginationMetadata::class,
        TestimonialsEntity::class
    ],
    version = 25,
    exportSchema = false
)
@TypeConverters(DataConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val eventDao: EventDao
    abstract val ticketDao: TicketDao
    abstract val userFeedback: UserFeedbackDao
    abstract val eventsFeedback: EventsFeedbackDao
    abstract val partnersDao: PartnersDao
    abstract val clubBioDao: ClubBioDao
    abstract val testimonialsDao: TestimonialsDao
}
