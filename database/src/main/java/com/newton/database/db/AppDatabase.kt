package com.newton.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.newton.database.converters.DataConverters
import com.newton.database.dao.ClubBioDao
import com.newton.database.dao.EventDao
import com.newton.database.dao.EventsFeedbackDao
import com.newton.database.dao.PartnersDao
import com.newton.database.dao.TestimonialsDao
import com.newton.database.dao.TicketDao
import com.newton.database.dao.UserDao
import com.newton.database.dao.UserFeedbackDao
import com.newton.database.entities.ClubBioEntity
import com.newton.database.entities.EventEntity
import com.newton.database.entities.EventPaginationMetadata
import com.newton.database.entities.EventsFeedbackEntity
import com.newton.database.entities.PartnerEntity
import com.newton.database.entities.PartnersDataEntity
import com.newton.database.entities.TestimonialsEntity
import com.newton.database.entities.TicketsEntity
import com.newton.database.entities.UserEntity
import com.newton.database.entities.UserFeedbackEntity

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