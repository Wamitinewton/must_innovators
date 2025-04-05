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
