package com.newton.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.newton.database.converters.DataConverters
import com.newton.database.dao.EventDao
import com.newton.database.dao.PartnersDao
import com.newton.database.dao.TicketDao
import com.newton.database.dao.UserDao
import com.newton.database.dao.UserFeedbackDao
import com.newton.database.entities.EventEntity
import com.newton.database.entities.PartnersDataEntity
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
    ],

    version = 21,
    exportSchema = false
)

@TypeConverters(DataConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val eventDao: EventDao
    abstract val ticketDao: TicketDao
    abstract val userFeedback: UserFeedbackDao
    abstract val partnersDao: PartnersDao
}